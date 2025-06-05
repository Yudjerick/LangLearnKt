package com.example.langlearnkt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langlearnkt.data.entities.Lesson
import com.example.langlearnkt.data.entities.OrderTask
import com.example.langlearnkt.data.entities.Task
import com.example.langlearnkt.data.entities.TitleParagraphTask
import com.example.langlearnkt.data.lesson1
import com.example.langlearnkt.data.repositories.LessonRepository
import com.example.langlearnkt.data.repositories.LessonResultRepository
import com.example.langlearnkt.viewmodels.tasks.OrderTaskViewState
import com.example.langlearnkt.viewmodels.tasks.TaskViewState
import com.example.langlearnkt.viewmodels.tasks.TitleParagraphTaskViewState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LessonViewModel() : ViewModel() {
    var lesson: Lesson = lesson1
    private val _currentTaskIdx = MutableLiveData<Int>(0)
    val currentTaskIdx: LiveData<Int> = _currentTaskIdx
    private val _taskStatus = MutableLiveData<TaskStatus>(TaskStatus.Unchecked)
    val taskStatus: LiveData<TaskStatus> = _taskStatus
    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = _isLoading

    var taskViewState: TaskViewState = getTaskViewModel(lesson.content.tasks[currentTaskIdx.value!!])

    private val _finishedEvent = MutableSharedFlow<Boolean>()
    val finishedEvent = _finishedEvent.asSharedFlow()

    var tasksResults = MutableList<Float?>(
        lesson.content.tasks.size,
        init = { _ -> null},
    )

    fun nextTask(){
        if(_currentTaskIdx.value!! < lesson.content.tasks.count() - 1){
            _currentTaskIdx.value = _currentTaskIdx.value!! + 1
            taskViewState = getTaskViewModel(lesson.content.tasks[currentTaskIdx.value!!])
            setTaskStatus(TaskStatus.Unchecked)
        }
        else{
            finishLesson()
        }
    }

    fun finishLesson(){
        viewModelScope.launch {
            LessonResultRepository().saveResult(
                FirebaseAuth.getInstance().currentUser!!.uid,
                lesson.metaData.id!!,
                tasksResults.count { x -> x == 1f } / tasksResults.size.toFloat()
            )
            _finishedEvent.emit(true)
        }

    }

    fun setTaskStatus(status: TaskStatus){
        _taskStatus.value = status
    }

    fun checkAndSaveTaskResult(): Boolean{
        var result = taskViewState.checkAnswer()
        tasksResults[currentTaskIdx.value!!] = if(result) 1f else 0f
        return result
    }

    private fun getTaskViewModel(task: Task): TaskViewState {
        when(task){
            is OrderTask -> return OrderTaskViewState(task)
            is TitleParagraphTask -> return TitleParagraphTaskViewState(task)
        }
    }

    fun loadLesson(lessonId: String){
        viewModelScope.launch {
            lesson = LessonRepository().getLesson(lessonId)!!
            _currentTaskIdx.value = 0
            taskViewState = getTaskViewModel(lesson.content.tasks[currentTaskIdx.value!!])
            setTaskStatus(TaskStatus.Unchecked)
            _isLoading.value = false
            tasksResults = MutableList<Float?>(
                lesson.content.tasks.size,
                init = { _ -> null},
            )
        }

    }

    enum class TaskStatus{
        Unchecked,
        Right,
        Wrong
    }
}
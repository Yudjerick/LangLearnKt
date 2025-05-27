package com.example.langlearnkt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.langlearnkt.data.entities.Lesson
import com.example.langlearnkt.data.entities.OrderTask
import com.example.langlearnkt.data.entities.Task
import com.example.langlearnkt.data.entities.TitleParagraphTask
import com.example.langlearnkt.data.lesson1
import com.example.langlearnkt.data.repositories.LessonRepository
import com.example.langlearnkt.ui.lessonMetaDataToLoad
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
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

    var taskViewModel: TaskViewModel = getTaskViewModel(lesson.content.tasks[currentTaskIdx.value!!])

    private val _finishedEvent = MutableSharedFlow<Boolean>()
    val finishedEvent = _finishedEvent.asSharedFlow()

    fun nextTask(){
        if(_currentTaskIdx.value!! < lesson.content.tasks.count() - 1){
            _currentTaskIdx.value = _currentTaskIdx.value!! + 1
            taskViewModel = getTaskViewModel(lesson.content.tasks[currentTaskIdx.value!!])
            setTaskStatus(TaskStatus.Unchecked)
        }
        else{
            finishLesson()
        }
    }

    fun finishLesson(){
        viewModelScope.launch {
            _finishedEvent.emit(true)
        }

    }

    fun setTaskStatus(status: TaskStatus){
        _taskStatus.value = status
    }

    private fun getTaskViewModel(task: Task): TaskViewModel{
        when(task){
            is OrderTask -> return OrderTaskViewModel(task)
            is TitleParagraphTask -> return TitleParagraphTaskViewModel(task)
        }
    }

    fun loadLesson(){
        viewModelScope.launch {
            lessonMetaDataToLoad.metadata?.let {
                lesson = LessonRepository().getLesson(it)!!
                _currentTaskIdx.value = 0
                taskViewModel = getTaskViewModel(lesson.content.tasks[currentTaskIdx.value!!])
                setTaskStatus(TaskStatus.Unchecked)
                _isLoading.value = false
            }
        }

    }

    enum class TaskStatus{
        Unchecked,
        Right,
        Wrong
    }
}
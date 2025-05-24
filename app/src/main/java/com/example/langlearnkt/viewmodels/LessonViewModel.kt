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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LessonViewModel() : ViewModel() {
    val lesson = lesson1
    private val _currentTaskIdx = MutableLiveData<Int>(0)
    val currentTaskIdx: LiveData<Int> = _currentTaskIdx
    private val _taskStatus = MutableLiveData<TaskStatus>(TaskStatus.Unchecked)
    val taskStatus: LiveData<TaskStatus> = _taskStatus

    var taskViewModel: TaskViewModel = getTaskViewModel(lesson.tasks[currentTaskIdx.value!!])

    private val _finishedEvent = MutableSharedFlow<Boolean>()
    val finishedEvent = _finishedEvent.asSharedFlow()

    fun nextTask(){
        if(_currentTaskIdx.value!! < lesson.tasks.count() - 1){
            _currentTaskIdx.value = _currentTaskIdx.value!! + 1
            taskViewModel = getTaskViewModel(lesson.tasks[currentTaskIdx.value!!])
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

    enum class TaskStatus{
        Unchecked,
        Right,
        Wrong
    }
}
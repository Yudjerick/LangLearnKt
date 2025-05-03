package com.example.langlearnkt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.langlearnkt.data.entities.Lesson
import kotlinx.coroutines.flow.MutableStateFlow

class LessonViewModel(
    val lesson: Lesson
) : ViewModel() {
    private val _currentTaskIdx = MutableLiveData<Int>(0)
    val currentTaskIdx: LiveData<Int> = _currentTaskIdx
    private val _taskStatus = MutableLiveData<TaskStatus>(TaskStatus.Unchecked)
    val taskStatus: LiveData<TaskStatus> = _taskStatus

    private val _showBottomSheet = MutableLiveData<Boolean>(false)
    val showBottomSheet: LiveData<Boolean> = _showBottomSheet

    fun nextTask(){
        if(_currentTaskIdx.value!! < lesson.tasks.count() - 1){
            _currentTaskIdx.value = _currentTaskIdx.value!! + 1
            setTaskStatus(TaskStatus.Unchecked)
        }
    }

    fun setTaskStatus(status: TaskStatus){
        _taskStatus.value = status
    }

    enum class TaskStatus{
        Unchecked,
        Right,
        Wrong
    }
}
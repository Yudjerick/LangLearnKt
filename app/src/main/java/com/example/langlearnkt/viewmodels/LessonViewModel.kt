package com.example.langlearnkt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.langlearnkt.data.entities.Lesson
import com.example.langlearnkt.data.entities.OrderTask
import com.example.langlearnkt.data.entities.Task
import com.example.langlearnkt.data.entities.TitleParagraphTask
import kotlinx.coroutines.flow.MutableStateFlow

class LessonViewModel(
    val lesson: Lesson
) : ViewModel() {
    private val _currentTaskIdx = MutableLiveData<Int>(0)
    val currentTaskIdx: LiveData<Int> = _currentTaskIdx
    private val _taskStatus = MutableLiveData<TaskStatus>(TaskStatus.Unchecked)
    val taskStatus: LiveData<TaskStatus> = _taskStatus

    var taskViewModel: TaskViewModel = getTaskViewModel(lesson.tasks[currentTaskIdx.value!!])

    fun nextTask(){
        if(_currentTaskIdx.value!! < lesson.tasks.count() - 1){
            _currentTaskIdx.value = _currentTaskIdx.value!! + 1
            taskViewModel = getTaskViewModel(lesson.tasks[currentTaskIdx.value!!])
            setTaskStatus(TaskStatus.Unchecked)

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
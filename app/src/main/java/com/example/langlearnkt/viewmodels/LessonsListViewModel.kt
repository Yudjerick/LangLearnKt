package com.example.langlearnkt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langlearnkt.data.entities.LessonMetaData
import com.example.langlearnkt.data.repositories.LessonRepository
import kotlinx.coroutines.launch

class LessonsListViewModel: ViewModel() {
    private val _lessonsMetaData: MutableLiveData<List<LessonMetaData>> =
        MutableLiveData<List<LessonMetaData>>(listOf())
    val lessonsMetaData: LiveData<List<LessonMetaData>> = _lessonsMetaData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadLessons(){
        viewModelScope.launch {
            _isLoading.value = true
            _lessonsMetaData.value = LessonRepository().getLessonsMetaDataList()
            _isLoading.value = false
        }
    }
}
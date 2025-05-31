package com.example.langlearnkt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.langlearnkt.data.entities.LessonMetaData
import com.example.langlearnkt.data.repositories.LessonRepository
import com.example.langlearnkt.data.repositories.LessonResultRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class LessonsListViewModel: ViewModel() {
    private val _lessonItems: MutableLiveData<List<LessonListItem>> =
        MutableLiveData<List<LessonListItem>>(listOf())
    val lessonItems: LiveData<List<LessonListItem>> = _lessonItems

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadLessons(){
        viewModelScope.launch {
            _isLoading.value = true
            _lessonItems.value = LessonRepository().getLessonsMetaDataList()
                .map { md -> LessonListItem(md) }
            _isLoading.value = false
            val results = mutableListOf<Float?>()
            val resultRepository = LessonResultRepository()
            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            lessonItems.value!!.forEach{ x ->
                _lessonItems.value = _lessonItems.value!!.map{ item ->
                    if(x != item) item
                    else LessonListItem(item.metaData, resultRepository.getResult(userId, x.metaData.id!!))
                }
            }
        }
    }
    data class LessonListItem(
        val metaData: LessonMetaData,
        val result: Float? = null
    )
}



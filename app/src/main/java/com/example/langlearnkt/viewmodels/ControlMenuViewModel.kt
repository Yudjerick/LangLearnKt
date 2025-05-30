package com.example.langlearnkt.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.langlearnkt.data.localcache.AppDatabase
import com.example.langlearnkt.data.repositories.LessonResultRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ControlMenuViewModel: ViewModel() {
    fun deleteResults(){
        viewModelScope.launch {
            LessonResultRepository().deleteResultsForUser(FirebaseAuth.getInstance().uid!!)
        }
    }

    fun clearLocalCache(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                AppDatabase.instance.clearAllTables()
            }
            catch (e:Exception) {
                Log.e("AAA", e.message.toString())
            }
        }
    }

    fun signOut(){
        FirebaseAuth.getInstance().signOut()
    }
}
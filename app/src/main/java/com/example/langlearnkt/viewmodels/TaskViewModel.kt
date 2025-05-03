package com.example.langlearnkt.viewmodels

import androidx.lifecycle.ViewModel

abstract class TaskViewModel: ViewModel() {
    abstract fun checkAnswer(): Boolean
}
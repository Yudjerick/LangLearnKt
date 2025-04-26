package com.example.langlearnkt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.langlearnkt.data.UserRepository

abstract class AuthViewModel: ViewModel()
{
    protected val userRepository = UserRepository()

    protected val _emailFieldText = MutableLiveData<String>("")
    val emailFieldText: LiveData<String> = _emailFieldText

    protected val _passwordFieldText = MutableLiveData<String>("")
    val passwordFieldText: LiveData<String> = _passwordFieldText

    protected val _waitForRequest = MutableLiveData(false)
    val waitForRequest: LiveData<Boolean> = _waitForRequest

    protected val _regSuccess = MutableLiveData<AuthRequestState>(AuthRequestState.WAIT)
    val regSuccess : LiveData<AuthRequestState> = _regSuccess

    fun updateEmailFieldValue(value: String){
        _emailFieldText.value = value
    }

    fun updatePasswordFieldValue(value: String){
        _passwordFieldText.value = value
    }
}

enum class AuthRequestState{
    WAIT,
    SUCCESS,
    FAILURE
}

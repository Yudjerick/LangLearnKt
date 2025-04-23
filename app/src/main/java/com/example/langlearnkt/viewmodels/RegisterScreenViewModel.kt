package com.example.langlearnkt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langlearnkt.data.UserRepository
import kotlinx.coroutines.launch

class RegisterScreenViewModel: ViewModel() {

    private val userRepository = UserRepository()

    private val _emailFieldText = MutableLiveData<String>("")
    val emailFieldText: LiveData<String> = _emailFieldText

    private val _passwordFieldText = MutableLiveData<String>("")
    val passwordFieldText: LiveData<String> = _passwordFieldText

    private val _waitForRequest = MutableLiveData(false)
    val waitForRequest: LiveData<Boolean> = _waitForRequest

    private val _regSuccess = MutableLiveData<RegRequestState>(RegRequestState.WAIT)
    val regSuccess : LiveData<RegRequestState> = _regSuccess

    fun updateRegisterFieldValue(value: String){
        _emailFieldText.value = value
    }

    fun updatePasswordFieldValue(value: String){
        _passwordFieldText.value = value
    }

    fun registerUserWithEmail(email: String, password: String){
        if(_waitForRequest.value!!){
           return
        }
        _waitForRequest.value = true
        viewModelScope.launch {
            val success = userRepository.registerUserWithEmail(email, password)
            _waitForRequest.postValue(false)
            if (success){
                _regSuccess.postValue(RegRequestState.SUCCESS)
            }
            else {
                _regSuccess.postValue(RegRequestState.FAILURE)
            }
        }
    }

    enum class RegRequestState{
        WAIT,
        SUCCESS,
        FAILURE
    }
}

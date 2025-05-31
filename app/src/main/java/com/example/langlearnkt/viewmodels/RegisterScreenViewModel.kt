package com.example.langlearnkt.viewmodels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RegisterScreenViewModel: AuthViewModel() {

    fun registerUserWithEmail(email: String, password: String){
        if(_waitForRequest.value!!){
           return
        }
        _waitForRequest.value = true
        viewModelScope.launch {
            val success = userRepository.registerUserWithEmail(email, password)
            _waitForRequest.postValue(false)
            if (success){
                _regSuccess.postValue(AuthRequestState.SUCCESS)
                loggedInEvent.emit(Unit)
            }
            else {
                _regSuccess.postValue(AuthRequestState.FAILURE)
            }
        }
    }
}

package com.example.langlearnkt.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.langlearnkt.viewmodels.RegisterScreenViewModel


@Composable
fun RegisterScreen(navController: NavController,
                   viewModel: RegisterScreenViewModel = viewModel()){
    val email = viewModel.emailFieldText.observeAsState("")
    val password = viewModel.passwordFieldText.observeAsState("")
    val waitForRequest = viewModel.waitForRequest.observeAsState(false)
    val regSuccess = viewModel.regSuccess.observeAsState(RegisterScreenViewModel.RegRequestState.WAIT)
    Column {
        TextField(
            value = email.value,
            onValueChange = {viewModel.updateRegisterFieldValue(it)},
            label = { Text("email") }
        )
        TextField(
            value = password.value,
            onValueChange = {viewModel.updatePasswordFieldValue(it)},
            label = { Text("password") }
        )
        Button(
            onClick = { viewModel.registerUserWithEmail(email.value, password.value) },
            enabled = !waitForRequest.value
        ) {
            if(!waitForRequest.value){
                Text("Регистрация")
            }
            else{
                CircularProgressIndicator()
            }
        }
        when(regSuccess.value){
            RegisterScreenViewModel.RegRequestState.SUCCESS ->
                Text(
                "Регистрация успешна",
                    color = Color.Green
                )
            RegisterScreenViewModel.RegRequestState.FAILURE ->
                Text(
                    "Ошибка регистрации",
                    color = Color.Red
                )
            else -> {}
        }
    }
}
package com.example.langlearnkt.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.langlearnkt.R
import com.example.langlearnkt.ui.components.LL_TextField
import com.example.langlearnkt.ui.screenPathes
import com.example.langlearnkt.viewmodels.LoginScreenViewModel
import com.example.langlearnkt.viewmodels.AuthRequestState

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = viewModel()
){
    val email = viewModel.emailFieldText.observeAsState("")
    val password = viewModel.passwordFieldText.observeAsState("")
    val waitForRequest = viewModel.waitForRequest.observeAsState(false)
    val regSuccess = viewModel.regSuccess.observeAsState(AuthRequestState.WAIT)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Image(
            painterResource(R.drawable.bg_waves),
            "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LL_TextField(
                value = email.value,
                onValueChange = { viewModel.updateEmailFieldValue(it) },
                labelText = "email"
            )
            LL_TextField(
                value = password.value,
                onValueChange = { viewModel.updatePasswordFieldValue(it) },
                labelText = "password",
            )
            Button(
                contentPadding = PaddingValues(horizontal = 80.dp),
                onClick = { viewModel.loginUserWithEmail(email.value, password.value) },
                enabled = !waitForRequest.value
            ) {
                if (!waitForRequest.value) {
                    Text("Вход")
                } else {
                    CircularProgressIndicator()
                }
            }
            when (regSuccess.value) {
                AuthRequestState.SUCCESS ->
                    Text(
                        "Вход выполнен",
                        color = Color.Green
                    )

                AuthRequestState.FAILURE ->
                    Text(
                        "Ошибка входа",
                        color = Color.Red
                    )

                else -> {}
            }
            Spacer(Modifier.height(70.dp))
            Text(buildAnnotatedString {
                append("Нет аккаунта? ")
                val link =
                    LinkAnnotation.Clickable(
                        "",
                        TextLinkStyles(SpanStyle(color = Color.Blue))
                    ) {
                        navController.navigate(screenPathes.register)
                    }
                withLink(link) { append("Зарегистрируйтесь") }
            })
            Spacer(Modifier.height(50.dp))
        }
    }
}
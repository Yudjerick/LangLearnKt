package com.example.langlearnkt.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.langlearnkt.R
import com.example.langlearnkt.ScreenRoutes
import com.example.langlearnkt.ui.components.LL_FunctionButton
import com.example.langlearnkt.ui.components.LL_FunctionContentButton
import com.example.langlearnkt.ui.components.LL_TextField
import com.example.langlearnkt.ui.theme.fontFamilies
import com.example.langlearnkt.viewmodels.AuthRequestState
import com.example.langlearnkt.viewmodels.RegisterScreenViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterScreenViewModel = viewModel()
){
    val email = viewModel.emailFieldText.observeAsState("")
    val password = viewModel.passwordFieldText.observeAsState("")
    val waitForRequest = viewModel.waitForRequest.observeAsState(false)
    val regSuccess = viewModel.regSuccess.observeAsState(AuthRequestState.WAIT)
    LaunchedEffect(Unit) {
        viewModel.loggedInEvent.collectLatest {
            navController.navigate(ScreenRoutes.LessonList)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            contentAlignment = Alignment.TopCenter
        ){
            Image(
                painterResource(R.drawable.bg_waves),
                "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Создайте аккаунт",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(vertical = 45.dp),
                fontFamily = fontFamilies.nunito,
                fontWeight = FontWeight(1000)
            )
        }
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
                labelText = "password"
            )
            LL_FunctionContentButton(
                onClick = { viewModel.registerUserWithEmail(email.value, password.value) },
                enabled = !waitForRequest.value
            ) {
                if (!waitForRequest.value) {
                    Text("Регистрация", fontFamily = fontFamilies.nunito,
                        fontSize = 20.sp, fontWeight = FontWeight.Black)
                } else {
                    CircularProgressIndicator()
                }
            }
            when (regSuccess.value) {
                AuthRequestState.SUCCESS -> {
                    Text(
                        "Регистрация успешна",
                        color = Color.Green
                    )
                }

                AuthRequestState.FAILURE ->
                    Text(
                        "Ошибка регистрации",
                        color = Color.Red
                    )

                else -> {}
            }
            Spacer(Modifier.height(70.dp))
            Text(
                buildAnnotatedString {
                    append("Уже зарегистрированы? ")
                    val link =
                        LinkAnnotation.Clickable(
                            "",
                            TextLinkStyles(SpanStyle(color = Color.Blue))
                        ) {
                            navController.navigate(ScreenRoutes.Login)
                        }
                    withLink(link) { append("Войдите в систему") }
                },
                color = Color.Gray,
                fontFamily = fontFamilies.nunito
            )
            Spacer(Modifier.height(50.dp))
        }
    }
}
package com.example.langlearnkt.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.langlearnkt.ScreenRoutes
import com.example.langlearnkt.ui.components.LL_FunctionButton
import com.example.langlearnkt.viewmodels.ControlMenuViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ControlMenuScreen(navController: NavController, viewModel: ControlMenuViewModel = viewModel()){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TransparentCloseButton({navController.navigate(ScreenRoutes.LessonList)})
            FirebaseAuth.getInstance().currentUser?.email?.let {
                Text(
                    text = it,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 15.dp)
                )
            }
        }
        HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
        LL_FunctionButton(
            onClick = {
                viewModel.signOut()
                navController.navigate(ScreenRoutes.Login)
            },
            text = "Выйти из аккаунта"
        )
        LL_FunctionButton(
            onClick = {viewModel.clearLocalCache()},
            text = "Очистить кэш"
        )
        LL_FunctionButton(
            onClick = {viewModel.deleteResults()},
            text = "Удалить результаты прохождения уроков"
        )
    }
}
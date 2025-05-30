package com.example.langlearnkt.ui.screens

import androidx.collection.scatterSetOf
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.langlearnkt.ui.screenPathes

@Composable
fun LessonFinishedScreen(navController: NavController){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Урок пройден",
            fontSize = 20.sp
        )
        Button(
            onClick = {navController.navigate(screenPathes.lessonsList)}
        ) {
            Text("Продолжить")
        }
    }

}
package com.example.langlearnkt.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.langlearnkt.data.entities.OrderTask
import com.example.langlearnkt.data.entities.TitleParagraphTask
import com.example.langlearnkt.viewmodels.LessonViewModel
import com.example.langlearnkt.viewmodels.OrderTaskViewModel
import com.example.langlearnkt.viewmodels.TitleParagraphTaskViewModel

@Composable
fun LessonScreen(navController: NavController, viewModel: LessonViewModel = viewModel()){
    val currentTaskIdx = viewModel.currentTaskIdx.observeAsState(0).value
    val currentTask = viewModel.lesson.tasks[currentTaskIdx]
    Column {
        Row(Modifier.height(50.dp)) {

            LinearProgressIndicator(progress = {
                (currentTaskIdx / viewModel.lesson.tasks.count()).toFloat()
            })
        }
        when(currentTask){
            is OrderTask -> OrderTaskScreen(navController, OrderTaskViewModel(currentTask))
            is TitleParagraphTask -> TitleParagraphTaskScreen(navController, TitleParagraphTaskViewModel(currentTask))
        }
        Button(
            onClick = { viewModel.nextTask() }
        ) {
            Text("Далее")
        }
    }
}
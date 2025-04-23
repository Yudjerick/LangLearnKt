package com.example.langlearnkt.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.langlearnkt.viewmodels.OrderTaskViewModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrderTaskScreen(navController: NavController, viewModel: OrderTaskViewModel = viewModel() ){
    val taskStatus = viewModel.taskStatus.observeAsState(OrderTaskViewModel.TaskStatus.Unchecked)
    Column {
        FlowRow(
            modifier = Modifier
                .wrapContentHeight()
                .defaultMinSize(minHeight = 50.dp)
        ){

            val givenAnswer by viewModel.givenAnswer.collectAsState()
            for (word in givenAnswer){
                Button({viewModel.removeWordFromAnswer(word)}) {
                    Text(word.bankWord.content)
                }
            }
        }
        FlowRow(
            modifier = Modifier
                .wrapContentHeight()
                .defaultMinSize(minHeight = 50.dp)
        ){
            val wordBank by viewModel.wordBank.collectAsState()
            for (word in wordBank){
                Button(
                    onClick = {viewModel.addWordToAnswer(word)},
                    enabled = word.buttonActive
                ) {
                    Text(word.content)
                }
            }
        }
        Button(onClick = { viewModel.checkAnswer() })
        {
            Text("Проверить")
        }
        when(taskStatus.value){
            OrderTaskViewModel.TaskStatus.Right ->
                Text(
                    text = "Верно",
                    color = Color.Green
                )
            OrderTaskViewModel.TaskStatus.Wrong ->
                Text(
                    text = "Неверно",
                    color = Color.Red
                )
            OrderTaskViewModel.TaskStatus.Unchecked -> {}
        }
        Button(onClick = {viewModel.nextTask()}) {
            Text("Дальше")
        }
    }
}
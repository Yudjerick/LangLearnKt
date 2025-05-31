package com.example.langlearnkt.ui.screens.tasks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.langlearnkt.ui.components.OrderTaskButton
import com.example.langlearnkt.ui.theme.fontFamilies
import com.example.langlearnkt.viewmodels.tasks.OrderTaskViewState


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrderTaskScreen(navController: NavController, viewModel: OrderTaskViewState){
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()

    ) {
        Text(
            text = viewModel.task.text,
            modifier = Modifier.padding(15.dp),
            color = Color.Gray,
            fontFamily = fontFamilies.nunito,
            fontSize = 18.sp
        )

        HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
        FlowRow(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 15.dp)
                .defaultMinSize(minHeight = 100.dp)
        ){

            val givenAnswer by viewModel.givenAnswer.collectAsState()
            for (word in givenAnswer){
                OrderTaskButton(
                    onClick = {viewModel.removeWordFromAnswer(word)},
                    text = word.bankWord.content
                )
            }
        }
        HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
        FlowRow(
            modifier = Modifier
                .wrapContentHeight()
                .defaultMinSize(minHeight = 50.dp)
                .padding(horizontal = 15.dp)
        ){
            val wordBank by viewModel.wordBank.collectAsState()
            for (word in wordBank){
                OrderTaskButton(
                    onClick = {viewModel.addWordToAnswer(word)},
                    text = word.content,
                    enabled = word.buttonActive
                )
            }
        }
    }
}
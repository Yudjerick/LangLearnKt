package com.example.langlearnkt.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.langlearnkt.viewmodels.OrderTaskViewState


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrderTaskScreen(navController: NavController, viewModel: OrderTaskViewState){
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()

    ) {
        FlowRow(
            modifier = Modifier
                .wrapContentHeight()
                .defaultMinSize(minHeight = 50.dp)
                .padding(horizontal = 15.dp)
        ){

            val givenAnswer by viewModel.givenAnswer.collectAsState()
            for (word in givenAnswer){
                Button(
                    onClick = {viewModel.removeWordFromAnswer(word)},
                    modifier = Modifier.padding(horizontal = 2.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp)
                ) {
                    Text(word.bankWord.content)
                }
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
                Button(
                    onClick = {viewModel.addWordToAnswer(word)},
                    enabled = word.buttonActive,
                    modifier = Modifier.padding(horizontal = 2.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp)
                ) {
                    Text(word.content)
                }
            }
        }
    }
}
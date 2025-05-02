package com.example.langlearnkt.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TransparentCloseButton({})
            RoundedProgressBar(
                (currentTaskIdx.toFloat() / viewModel.lesson.tasks.count().toFloat()),
                Color.Green,
                Color.Gray,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
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

@Composable
fun RoundedProgressBar(
    progress: Float, // 0f..1f
    filledColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    val progressPercent = progress.coerceIn(0f, 1f)
    val cornerRadius = 4.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
    ) {
        // Фон (незаполненная часть)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(cornerRadius))
                .background(color = backgroundColor)
        )

        // Заполненная часть
        Box(
            modifier = Modifier
                .fillMaxWidth(progressPercent)
                .fillMaxHeight()
                .clip(RoundedCornerShape(cornerRadius))
                .background(filledColor)
        )
    }
}

@Composable
fun TransparentCloseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconColor: Color = Color.Gray,
    backgroundColor: Color = Color.Transparent
) {
    Box(
        modifier = modifier
            .size(48.dp) // Размер кнопки
            .clip(CircleShape) // Круглая форма
            .clickable(onClick = onClick) // Обработка клика
            .background(backgroundColor, CircleShape), // Прозрачный фон
        contentAlignment = Center
    ) {
        Icon(
            imageVector = Icons.Default.Close, // Иконка крестика
            contentDescription = "Close",
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
    }
}
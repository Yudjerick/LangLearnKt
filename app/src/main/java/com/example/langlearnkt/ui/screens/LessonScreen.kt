package com.example.langlearnkt.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.langlearnkt.data.entities.OrderTask
import com.example.langlearnkt.data.entities.TitleParagraphTask
import com.example.langlearnkt.ui.screenPathes
import com.example.langlearnkt.viewmodels.LessonViewModel
import com.example.langlearnkt.viewmodels.OrderTaskViewModel
import com.example.langlearnkt.viewmodels.TaskViewModel
import com.example.langlearnkt.viewmodels.TitleParagraphTaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(navController: NavController, viewModel: LessonViewModel = viewModel()){


    val currentTaskIdx = viewModel.currentTaskIdx.observeAsState(0).value
    val currentTask = viewModel.lesson.tasks[currentTaskIdx]
    val taskStatus = viewModel.taskStatus.observeAsState(LessonViewModel.TaskStatus.Unchecked)
    val taskViewModel = viewModel.taskViewModel


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
        HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
        Box(Modifier.weight(1f)){
            when(currentTask){
                is OrderTask -> {
                    OrderTaskScreen(navController, taskViewModel as OrderTaskViewModel)
                }
                is TitleParagraphTask -> {
                    TitleParagraphTaskScreen(navController, taskViewModel as TitleParagraphTaskViewModel)
                }
            }
        }
        HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
        Button(
            onClick = { viewModel.setTaskStatus(if (taskViewModel.checkAnswer())
                LessonViewModel.TaskStatus.Right else LessonViewModel.TaskStatus.Wrong)},
            Modifier.padding(vertical = 10.dp)
        ) {
            Text("Проверить")
        }

        if(taskStatus.value != LessonViewModel.TaskStatus.Unchecked){
            ModalBottomSheet(
                onDismissRequest = {viewModel.nextTask()},
            )
            {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when(taskStatus.value){
                        LessonViewModel.TaskStatus.Right ->
                            Text(
                                text = "Верно",
                                color = Color.Green,
                                fontSize = 20.sp,
                            )
                        LessonViewModel.TaskStatus.Wrong ->
                            Text(
                                text = "Неверно",
                                color = Color.Red,
                                fontSize = 20.sp,
                            )
                        LessonViewModel.TaskStatus.Unchecked -> {}
                    }
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = {
                            viewModel.nextTask()
                        },
                        contentPadding = PaddingValues(horizontal = 50.dp)
                    ) {
                        Text("Далее")
                    }
                    Spacer(Modifier.height(60.dp))
                }

            }
        }

    }
    LaunchedEffect(Unit) {
        viewModel.finishedEvent.collectLatest {
            navController.navigate(screenPathes.lessonFinished)
        }
         // Проверяем без ViewModel

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
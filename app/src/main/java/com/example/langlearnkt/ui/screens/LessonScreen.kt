package com.example.langlearnkt.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.langlearnkt.R
import com.example.langlearnkt.ScreenRoutes
import com.example.langlearnkt.data.entities.OrderTask
import com.example.langlearnkt.data.entities.TitleParagraphTask
import com.example.langlearnkt.ui.components.LL_FunctionButton
import com.example.langlearnkt.ui.screens.tasks.OrderTaskScreen
import com.example.langlearnkt.ui.screens.tasks.TitleParagraphTaskScreen
import com.example.langlearnkt.viewmodels.LessonViewModel
import com.example.langlearnkt.viewmodels.tasks.OrderTaskViewState
import com.example.langlearnkt.viewmodels.tasks.TitleParagraphTaskViewState
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    navController: NavController,
    lessonId: String,
    viewModel: LessonViewModel = viewModel()
){

    LaunchedEffect(Unit) { viewModel.loadLesson(lessonId) }
    LaunchedEffect(Unit) {
        viewModel.finishedEvent.collectLatest {
            navController.navigate(ScreenRoutes.LessonFinished(viewModel.tasksResults.map { x -> x!! }))
        }
    }
    if(viewModel.isLoading.observeAsState().value!!){
        CircularProgressIndicator()
        return
    }

    val currentTaskIdx = viewModel.currentTaskIdx.observeAsState(0).value
    val currentTask = viewModel.lesson.content.tasks[currentTaskIdx]
    val taskStatus = viewModel.taskStatus.observeAsState(LessonViewModel.TaskStatus.Unchecked)
    val taskViewModel = viewModel.taskViewState



    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TransparentCloseButton({navController.navigate(ScreenRoutes.LessonList)})
            RoundedProgressBar(
                (currentTaskIdx.toFloat() / viewModel.lesson.content.tasks.count().toFloat()),
                colorResource(R.color.green_right),
                Color.Gray,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
        HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
        Box(Modifier.weight(1f)){
            when(currentTask){
                is OrderTask -> {
                    OrderTaskScreen(navController, taskViewModel as OrderTaskViewState)
                }
                is TitleParagraphTask -> {
                    TitleParagraphTaskScreen(navController, taskViewModel as TitleParagraphTaskViewState)
                }
            }
        }
        HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
        LL_FunctionButton(
            onClick = { viewModel.setTaskStatus(if (viewModel.checkAndSaveTaskResult())
                LessonViewModel.TaskStatus.Right else LessonViewModel.TaskStatus.Wrong)},
            text = "Проверить"
        )
        if(taskStatus.value != LessonViewModel.TaskStatus.Unchecked){
            ModalBottomSheet(
                onDismissRequest = {viewModel.nextTask()},
                containerColor =
                    if(taskStatus.value == LessonViewModel.TaskStatus.Right)
                        colorResource(R.color.green_right)
                    else
                        colorResource(R.color.red_wrong)
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
                                color = Color.White,
                                fontSize = 20.sp,
                            )
                        LessonViewModel.TaskStatus.Wrong ->
                            Text(
                                text = "Неверно",
                                color = Color.White,
                                fontSize = 20.sp,
                            )
                        LessonViewModel.TaskStatus.Unchecked -> {}
                    }
                    Spacer(Modifier.height(10.dp))
                    LL_FunctionButton(
                        onClick = {
                            viewModel.nextTask()
                        },
                        text = "Далее"
                    )
                    Spacer(Modifier.height(60.dp))
                }

            }
        }

    }

}

@Composable
fun RoundedProgressBar(
    progress: Float,
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
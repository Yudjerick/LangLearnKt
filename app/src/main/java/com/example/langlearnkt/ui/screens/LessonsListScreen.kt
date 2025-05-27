package com.example.langlearnkt.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.langlearnkt.R
import com.example.langlearnkt.data.entities.LessonMetaData
import com.example.langlearnkt.ui.lessonMetaDataToLoad
import com.example.langlearnkt.ui.screenPathes
import com.example.langlearnkt.viewmodels.LessonsListViewModel


@Composable
fun LessonsListScreen(
    navController: NavController,
    viewModel: LessonsListViewModel = viewModel()
){
    val lessonsMetadata = viewModel.lessonsMetaData.observeAsState(emptyList())
    val isLoading = viewModel.isLoading.observeAsState(false)

    LaunchedEffect(Unit) {
        viewModel.loadLessons()
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TransparentCloseButton({})
        }
        HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
        if(lessonsMetadata.value.isEmpty()){
            CircularProgressIndicator()
        }
        else {
            LazyColumn {
                items(lessonsMetadata.value) { metaData ->
                    LessonListItem(metaData, navController)
                }
            }
        }
    }
}

@Composable
fun LessonListItem(metadata: LessonMetaData, navController: NavController){
    Row(
        modifier = Modifier
        .padding(horizontal = 15.dp, vertical = 0.dp)
        .fillMaxWidth()
        .clickable {
            lessonMetaDataToLoad.metadata = metadata
            navController.navigate(screenPathes.lesson)
        }
    ) {
        Box(
            modifier = Modifier
                .weight(2f)  // Занимаем всю доступную высоту
                .aspectRatio(1f)  // Сохраняем квадратную форму (опционально)
        ) {
            Image(
                painter = painterResource(R.drawable.roadmap_level_current),  // Используем painterResource вместо ImageBitmap
                contentDescription = "lesson icon",
                contentScale = ContentScale.FillBounds,  // или ContentScale.FillBounds
                modifier = Modifier.fillMaxSize()  // Занимаем весь доступный Box
            )
        }
        Column(
            modifier = Modifier.weight(6f)  // Занимаем оставшееся пространство
        ){
            Spacer(Modifier.height(10.dp))
            metadata.title?.let {
                Text(
                    text = it,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    color = Color.Gray
                )
            }
            metadata.description?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    color = Color.LightGray
                )
            }
            Spacer(Modifier.height(10.dp))
        }
    }

}
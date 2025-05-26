package com.example.langlearnkt.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
                    Button({
                        lessonMetaDataToLoad.metadata = metaData
                        navController.navigate(screenPathes.lesson)
                    })
                    {
                        Text(metaData.id!!)
                    }
                }
            }
        }
    }
}
package com.example.langlearnkt.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.gestures.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignMenuScreen(
    navController: NavController,
    userName: String = "Иван Иванов"
) {
    val courses = listOf(
        Course(
            id = 1,
            title = "Beginner English",
            description = "Основы английского для начинающих",
            imageUrl = "https://example.com/beginner.jpg"
        ),
        Course(
            id = 2,
            title = "Intermediate English",
            description = "Для тех, кто уже знает основы",
            imageUrl = "https://example.com/intermediate.jpg"
        ),
        Course(
            id = 3,
            title = "Advanced English",
            description = "Продвинутый уровень для свободного общения",
            imageUrl = "https://example.com/advanced.jpg"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    IconButton(onClick = { /* Open profile */ }) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Profile")
                    }
                    Text(
                        text = userName,
                        modifier = Modifier.padding(end = 16.dp),
                        fontSize = 16.sp
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            SwipeableCards(
                courses = courses,
                onCardClicked = { course ->
                    navController.navigate("courseDetails/${course.id}")
                }
            )
        }
    }
}

@Composable
fun SwipeableCards(
    courses: List<Course>,
    onCardClicked: (Course) -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }
    val swipeState = remember { mutableStateOf(0f) }
    val maxOffset = with(LocalDensity.current) { 100.dp.toPx() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        contentAlignment = Alignment.Center
    ) {
        // Показываем предыдущую и следующую карточки (если есть)
        if (currentIndex > 0) {
            CourseCard(
                course = courses[currentIndex - 1],
                offset = (swipeState.value + maxOffset).coerceAtMost(maxOffset),
                onClick = { onCardClicked(courses[currentIndex - 1]) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .graphicsLayer {
                        scaleX = 0.9f
                        scaleY = 0.9f
                        alpha = 0.7f
                    }
            )
        }

        if (currentIndex < courses.size - 1) {
            CourseCard(
                course = courses[currentIndex + 1],
                offset = (swipeState.value - maxOffset).coerceAtLeast(-maxOffset),
                onClick = { onCardClicked(courses[currentIndex + 1]) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .graphicsLayer {
                        scaleX = 0.9f
                        scaleY = 0.9f
                        alpha = 0.7f
                    }
            )
        }

        // Основная карточка
        CourseCard(
            course = courses[currentIndex],
            offset = swipeState.value,
            onClick = { onCardClicked(courses[currentIndex]) },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (swipeState.value > maxOffset / 2 && currentIndex > 0) {
                                currentIndex--
                            } else if (swipeState.value < -maxOffset / 2 && currentIndex < courses.size - 1) {
                                currentIndex++
                            }
                            swipeState.value = 0f
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            swipeState.value += dragAmount
                            change.consume()
                        }
                    )
                }
        )
    }
}

@Composable
fun CourseCard(
    course: Course,
    offset: Float = 0f,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val offsetInDp = with(LocalDensity.current) { offset.toDp() }

    Card(
        modifier = modifier
            .offset { IntOffset(offset.roundToInt(), 0) }
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = course.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = course.description,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onClick) {
                Text("Начать курс")
            }
        }
    }
}

data class Course(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
)

@Preview(showBackground = true)
@Composable
fun PreviewCampaingMenuScreen() {
    MaterialTheme {
        CampaignMenuScreen(navController = rememberNavController())
    }
}
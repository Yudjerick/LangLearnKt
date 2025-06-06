package com.example.langlearnkt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.Room
import com.example.langlearnkt.data.localcache.AppDatabase
import com.example.langlearnkt.ui.screens.LoginScreen
import com.example.langlearnkt.ui.screens.RegisterScreen
import com.example.langlearnkt.ui.screens.ControlMenuScreen
import com.example.langlearnkt.ui.screens.LessonFinishedScreen
import com.example.langlearnkt.ui.screens.LessonScreen
import com.example.langlearnkt.ui.screens.LessonsListScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "db"
        ).build()
        AppDatabase.instance = db

        lifecycleScope.launch {
            //LessonRepository().saveLesson(lesson1)
            //LessonRepository().saveLesson(lesson2)
        }
        var startScreenPath: Any = ScreenRoutes.Login
        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            startScreenPath = ScreenRoutes.LessonList
        }

        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = startScreenPath,
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300)) },
                popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300)) },
                builder = {
                    composable<ScreenRoutes.LessonFinished> {
                        backStackEntry ->
                        val route: ScreenRoutes.LessonFinished = backStackEntry.toRoute()
                        LessonFinishedScreen(navController, route.results)
                    }
                    composable<ScreenRoutes.Login> {
                        LoginScreen(navController)
                    }
                    composable<ScreenRoutes.Register> {
                        RegisterScreen(navController)
                    }
                    composable<ScreenRoutes.Lesson>{
                        backStackEntry ->
                        val lessonRoute: ScreenRoutes.Lesson = backStackEntry.toRoute()
                        LessonScreen(navController, lessonRoute.lessonId)
                    }
                    composable<ScreenRoutes.LessonList> {
                        LessonsListScreen(navController)
                    }
                    composable<ScreenRoutes.ControlMenu> {
                        ControlMenuScreen(navController)
                    }
                }
            )
        }
    }
}


class ScreenRoutes{
    @Serializable
    data class Lesson(val lessonId: String)
    @Serializable
    data class LessonFinished(val results: List<Float>)
    //data class LessonFinished(val results: String)
    @Serializable
    object Login
    @Serializable
    object Register
    @Serializable
    object LessonList
    @Serializable
    object ControlMenu
}
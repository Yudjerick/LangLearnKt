package com.example.langlearnkt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.langlearnkt.data.lesson1
import com.example.langlearnkt.data.repositories.LessonRepository
import com.example.langlearnkt.ui.screens.LoginScreen
import com.example.langlearnkt.ui.screens.RegisterScreen
import com.example.langlearnkt.ui.screenPathes
import com.example.langlearnkt.ui.screens.CampaignMenuScreen
import com.example.langlearnkt.ui.screens.LessonFinishedScreen
import com.example.langlearnkt.ui.screens.LessonScreen
import com.example.langlearnkt.ui.screens.LessonsListScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
// ...
// Initialize Firebase Auth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.firestore
        lifecycleScope.launch {
            //LessonRepository().saveLesson(lesson1)
        }

        var startScreenPath = screenPathes.login
        var currentUser = Firebase.auth.currentUser
        //currentUser = null
        if (currentUser != null) {
            startScreenPath = screenPathes.lessonsList
        }



        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController, startDestination = startScreenPath, builder = {
                    composable(screenPathes.lessonFinished) {
                        LessonFinishedScreen(navController)
                    }
                    composable(screenPathes.login) {
                        LoginScreen(navController)
                    }
                    composable(screenPathes.register) {
                        RegisterScreen(navController)
                    }
                    composable(screenPathes.campaignMenu) {
                        CampaignMenuScreen(navController)
                    }
                    composable(route = screenPathes.lesson) {
                        LessonScreen(navController)
                    }
                    composable(screenPathes.lessonsList) {
                        LessonsListScreen(navController)
                    }

            })
        }
    }
}


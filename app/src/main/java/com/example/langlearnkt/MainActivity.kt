package com.example.langlearnkt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.langlearnkt.data.entities.Lesson
import com.example.langlearnkt.data.entities.OrderTask
import com.example.langlearnkt.data.lesson1
import com.example.langlearnkt.ui.screens.LoginScreen
import com.example.langlearnkt.ui.screens.OrderTaskScreen
import com.example.langlearnkt.viewmodels.OrderTaskViewModel
import com.example.langlearnkt.ui.screens.RegisterScreen
import com.example.langlearnkt.ui.screens.TitleParagraphTaskScreen
import com.example.langlearnkt.ui.screenPathes
import com.example.langlearnkt.ui.screens.CampaignMenuScreen
import com.example.langlearnkt.ui.screens.LessonScreen
import com.example.langlearnkt.viewmodels.LessonViewModel
import com.example.langlearnkt.viewmodels.TitleParagraphTaskViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
// ...
// Initialize Firebase Auth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var startScreenPath = screenPathes.login
        var currentUser = Firebase.auth.currentUser
        //currentUser = null
        if (currentUser != null) {
            startScreenPath = screenPathes.lesson
        }



        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController, startDestination = startScreenPath, builder = {
                    composable(screenPathes.login) {
                        LoginScreen(navController)
                    }
                    composable(screenPathes.register) {
                        RegisterScreen(navController)
                    }
                    composable(screenPathes.campaignMenu) {
                        CampaignMenuScreen(navController)
                    }
                    composable(screenPathes.lesson) {
                        LessonScreen(navController, LessonViewModel(lesson1))
                    }
            })
        }
    }
}
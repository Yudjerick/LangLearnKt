package com.example.langlearnkt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.langlearnkt.data.entities.Lesson
import com.example.langlearnkt.data.entities.OrderTask
import com.example.langlearnkt.ui.screens.LoginScreen
import com.example.langlearnkt.ui.screens.OrderTaskScreen
import com.example.langlearnkt.viewmodels.OrderTaskViewModel
import com.example.langlearnkt.ui.screens.RegisterScreen
import com.example.langlearnkt.ui.screens.TitleParagraphTaskScreen
import com.example.langlearnkt.ui.screenPathes
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
        currentUser = null
        if (currentUser != null) {
            startScreenPath = screenPathes.orderTask
        }

        val lesson = Lesson(listOf(
            OrderTask(0,"","", listOf("we", "are", "the", "champions"), listOf("losers")),
            OrderTask(0,"","", listOf("Hello", "world"), listOf("aaaaa"))
        ))

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
                composable(screenPathes.orderTask) {
                    OrderTaskScreen(navController, OrderTaskViewModel(lesson))
                }
                composable(screenPathes.titleParagraphTask) {
                    TitleParagraphTaskScreen(TitleParagraphTaskViewModel(applicationContext))
                }
            })
        }
    }
}
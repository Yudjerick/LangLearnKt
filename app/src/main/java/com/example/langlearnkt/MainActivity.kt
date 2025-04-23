package com.example.langlearnkt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.langlearnkt.data.Lesson
import com.example.langlearnkt.data.OrderTask
import com.example.langlearnkt.ui.screens.OrderTaskScreen
import com.example.langlearnkt.viewmodels.OrderTaskViewModel
import com.example.langlearnkt.ui.screens.RegisterScreen
import com.example.langlearnkt.ui.screens.TitleParagraphTaskScreen
import com.example.langlearnkt.viewmodels.TitleParagraphTaskViewModel

class MainActivity : ComponentActivity() {
// ...
// Initialize Firebase Auth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lesson = Lesson(listOf(
            OrderTask(0,"","", listOf("we", "are", "the", "champions"), listOf("losers")),
            OrderTask(0,"","", listOf("Hello", "world"), listOf("aaaaa"))
        ))

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "TitleParagraphTask", builder = {
                composable("OrderTask") {
                    OrderTaskScreen(navController, OrderTaskViewModel(lesson))
                }
                composable("Register") {
                    RegisterScreen(navController)
                }
                composable("TitleParagraphTask") {
                    TitleParagraphTaskScreen(TitleParagraphTaskViewModel(applicationContext))
                }
            })
        }
    }
}
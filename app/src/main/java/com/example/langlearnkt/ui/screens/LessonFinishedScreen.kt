package com.example.langlearnkt.ui.screens

import androidx.collection.scatterSetOf
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.langlearnkt.ui.components.LL_FunctionButton
import com.example.langlearnkt.ui.screenPathes
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

@Composable
fun LessonFinishedScreen(navController: NavController){

    KonfettiView(
        parties = listOf(
            Party(
                speed = 10f,
                maxSpeed = 30f,
                damping = 0.9f,
                angle = Angle.RIGHT - 45,
                spread = Spread.WIDE,
                colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                emitter = Emitter(duration = 400, TimeUnit.MILLISECONDS).perSecond(100),
                position = Position.Relative(0.0, 0.3)
            ),
            Party(
                speed = 10f,
                maxSpeed = 30f,
                damping = 0.9f,
                angle = Angle.LEFT + 45,
                spread = Spread.WIDE,
                colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                emitter = Emitter(duration = 400, TimeUnit.MILLISECONDS).perSecond(100),
                position = Position.Relative(1.0, 0.3)
            )
        ),
        modifier = Modifier.fillMaxSize()
    )



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Урок пройден",
            fontSize = 20.sp
        )
        LL_FunctionButton(
            onClick = {navController.navigate(screenPathes.lessonsList)},
            text = "Продолжить"
        )
    }

}
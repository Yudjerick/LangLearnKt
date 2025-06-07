package com.example.langlearnkt.ui.screens

import androidx.collection.scatterSetOf
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.langlearnkt.R
import com.example.langlearnkt.ScreenRoutes
import com.example.langlearnkt.ui.components.LL_FunctionButton
import com.example.langlearnkt.ui.theme.fontFamilies
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LessonFinishedScreen(navController: NavController, results: List<Float>){
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
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Урок пройден!",
            fontSize = 30.sp,
            color = Color.Gray,
            fontFamily = fontFamilies.nunito,
            modifier = Modifier.padding(vertical = 15.dp)

        )
        val pieChartData = PieChartData(
            slices = listOf(
                PieChartData.Slice(
                    "Right",
                    results.count { x -> x == 1f }.toFloat() / results.count().toFloat(),
                    colorResource(R.color.green_right)
                ),
                PieChartData.Slice(
                    "Wrong",
                    results.count { x -> x == 0f }.toFloat() / results.count().toFloat(),
                    colorResource(R.color.red_wrong)
                ),
            ),
            plotType = PlotType.Donut
        )
        val pieChartConfig = PieChartConfig(
            isAnimationEnable = true,
            showSliceLabels = false,
            animationDuration = 1500
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Box(modifier = Modifier.padding(horizontal = 60.dp).background(color = Color.Transparent)){
                PieChart(
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    pieChartData,
                    pieChartConfig
                )
            }
            Spacer(Modifier.height(30.dp))
            FlowRow(modifier = Modifier.height(20.dp).padding(horizontal = 15.dp)) {
                for(result in results){
                    var color: Color
                    if(result == 1.0f){
                        color = colorResource(R.color.green_right)
                    }
                    else{
                        color = colorResource(R.color.red_wrong)
                    }
                    Box(modifier = Modifier.padding(horizontal = 5.dp).fillMaxHeight().aspectRatio(1f)
                        .clip(RoundedCornerShape(20.dp)).background(color)
                    )
                }
            }
        }

        //Spacer(Modifier.height(50.dp))


        //Spacer(Modifier.height(50.dp))
        LL_FunctionButton(
            onClick = {navController.navigate(ScreenRoutes.LessonList)},
            text = "Продолжить"
        )
    }

}
package com.example.langlearnkt.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.langlearnkt.R
import org.w3c.dom.Text
@Preview(showBackground = true)
@Composable
fun TitleParagraphTaskScreen(){
    Column {
        Box(
            Modifier.fillMaxHeight(0.6f).padding(horizontal = 15.dp)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
            ) {
                Spacer(Modifier.height(20.dp))
                Text(
                    stringResource(R.string.test_paragraph1),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                Text(
                    stringResource(R.string.test_paragraph2),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                Text(
                    stringResource(R.string.test_paragraph3),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                Spacer(Modifier.height(20.dp))
            }
        }
        HorizontalDivider(thickness = 2.dp, color = Color.LightGray)

        Row() {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(horizontal = 15.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(15.dp))
                repeat(10){

                    ParagraphTitleMapItem("A", "1")

                }
                Spacer(Modifier.height(15.dp))
            }
            VerticalDivider(thickness = 2.dp, color = Color.LightGray)
            Column (
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .verticalScroll(rememberScrollState())
            ){
                Spacer(Modifier.height(15.dp))
                repeat(10){
                    Button({}) {
                        Text("1. Sample Answer Option")
                    }
                }
                Spacer(Modifier.height(15.dp))
            }
        }
    }
}

@Composable
fun ParagraphTitleMapItem(paragraphLetter: String, titleNumber: String){
    Row(
        Modifier.fillMaxWidth()
            .padding(vertical = 5.dp)
            .height(35.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Button(
            modifier = Modifier.size(35.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            onClick = {}
        ) {
            Text(paragraphLetter)
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .width(40.dp)
        ) {
            Button(
                modifier = Modifier
                    .weight(1f )
                    .fillMaxWidth(),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent ),
                onClick = {}
            ) {
                Text(
                    text = titleNumber,
                    color = Color.Black
                )
            }
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(size = 4.dp))
                    .background(Color.Black)
            )
        }
    }
}
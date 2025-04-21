package com.example.langlearnkt.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.langlearnkt.R
import org.w3c.dom.Text

@Composable
fun TitleParagraphTaskScreen(){
    Column {
        Box(
            Modifier.fillMaxHeight(0.6f).padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.big_text),
                    fontSize = 16.sp
                )
            }
        }
        Row {
            Column(
                modifier = Modifier.fillMaxWidth(0.3f)
            ) {
                repeat(6){
                    Row{
                        Button(
                            modifier = Modifier.wrapContentSize(),
                            onClick = {}
                        ) {
                            Text("A")
                        }
                        Button(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(Color.White),
                            onClick = {}
                        ) {
                            Text("__")
                        }
                    }
                }
            }
            Column {
                Button({}) {
                    Text("1. Sample Answer Option")
                }
            }
        }
    }

}
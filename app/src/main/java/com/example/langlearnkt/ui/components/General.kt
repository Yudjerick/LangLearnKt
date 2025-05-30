package com.example.langlearnkt.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.langlearnkt.R
import com.example.langlearnkt.ui.theme.fontFamilies
import com.example.langlearnkt.viewmodels.LessonViewModel

@Preview
@Composable
fun OrderTaskButton(onClick: () -> Unit = {}, text: String = "Text", enabled: Boolean = true){
    Button(
        onClick = onClick,
        colors = ButtonColors(Color.White,
            Color.Gray, Color.Gray, Color.Gray),
        border = BorderStroke(2.dp, Color.Gray),
        shape = RoundedCornerShape(30),
        enabled = enabled,
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.padding(4.dp)
            .defaultMinSize(0.dp)
            .wrapContentWidth()
        ) {
        Text(
            text = text,
            fontFamily = fontFamilies.nunito,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.defaultMinSize(0.dp)
                .wrapContentWidth()
        )
    }
}

@Composable
fun LL_TextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String)
{
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = labelText, fontFamily = fontFamilies.nunito) },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.border(2.dp, Color.Gray, RoundedCornerShape(10.dp)),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun LL_FunctionButton(onClick: () -> Unit, text: String, enabled: Boolean = true){
    Button(
        onClick = onClick,
        Modifier.padding(vertical = 10.dp, horizontal = 20.dp).fillMaxWidth(),
        colors = ButtonColors(
            colorResource(R.color.purple_200),
            Color.White,
            Color.Gray,
            Color.Gray
        ),
        border = BorderStroke(2.dp, colorResource(R.color.dark_purple)),
        shape = RoundedCornerShape(30),
        enabled = enabled
    ) {
        Text(
            text,
            fontFamily = fontFamilies.nunito,
            fontWeight = FontWeight.Black,
            fontSize = 20.sp
        )
    }
}

@Composable
fun LL_FunctionContentButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable() (RowScope.() -> Unit)
){
    Button(
        onClick = onClick,
        Modifier.padding(vertical = 10.dp, horizontal = 20.dp).fillMaxWidth(),
        colors = ButtonColors(
            colorResource(R.color.purple_200),
            Color.White,
            Color.Gray,
            Color.Gray
        ),
        border = BorderStroke(2.dp, colorResource(R.color.dark_purple)),
        shape = RoundedCornerShape(30),
        enabled = enabled
    ) {
        content()
    }
}
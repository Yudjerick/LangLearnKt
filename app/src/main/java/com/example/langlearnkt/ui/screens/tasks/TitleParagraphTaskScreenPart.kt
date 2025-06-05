package com.example.langlearnkt.ui.screens.tasks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.langlearnkt.R
import com.example.langlearnkt.data.entities.ParagraphData
import com.example.langlearnkt.ui.theme.fontFamilies
import com.example.langlearnkt.viewmodels.tasks.TitleParagraphTaskViewState
import kotlinx.coroutines.launch

@Composable
fun TitleParagraphTaskScreen(navController: NavController, viewModel: TitleParagraphTaskViewState){
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val paragraphs = viewModel.paragraphs.observeAsState(listOf()).value
    val titles = viewModel.titleBank.observeAsState(listOf()).value
    Column(Modifier.fillMaxHeight()) {
        Box(
            Modifier.fillMaxHeight(0.6f).padding(horizontal = 15.dp)
        ) {
            LazyColumn(state = scrollState) {
                item() { Spacer(Modifier.height(20.dp)) }
                items( paragraphs ){ paragraph ->
                    Text(
                        fontFamily = fontFamilies.nunito,
                        text = paragraph.text,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                    )
                }
                item() { Spacer(Modifier.height(20.dp)) }
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
                val selectedMap = viewModel.selectedMap.observeAsState().value
                Spacer(Modifier.height(15.dp))
                for(map in viewModel.titleParagraphMaps.observeAsState(listOf()).value)
                {
                    ParagraphTitleMapItem(
                        mapData = map,
                        selectedMap,
                        onMapClick = { viewModel.onMapClick(map) },
                        onLetterClick = {
                            scope.launch {
                                scrollState.animateScrollToItem(paragraphs.indexOf(map.letterParagraph) + 1 )
                            }
                        }
                    )
                }
                Spacer(Modifier.height(15.dp))
            }
            VerticalDivider(thickness = 2.dp, color = Color.LightGray)
            LazyColumn(modifier = Modifier.padding(horizontal = 15.dp)) {
                item { Spacer(Modifier.height(15.dp)) }
                items(titles) {title ->
                    TitleButton(
                        onClick = { viewModel.onTitleClick(title) },
                        enabled = title.active,
                        highlighted = title == viewModel.selectedTitle.observeAsState().value,
                        text = title.number.toString() + ". " + title.paragraph.title,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
                item { Spacer(Modifier.height(15.dp)) }
            }
        }
    }
}

//TODO : write viewmodel with ParagraphData class, use
// objects of this class as key for button to find right paragraph text

@Composable
fun ParagraphTitleMapItem(
    mapData: TitleParagraphTaskViewState.TitleParagraphMap,
    selectedMap: TitleParagraphTaskViewState.TitleParagraphMap?,
    onMapClick: () -> Unit,
    onLetterClick: () -> Unit
){
    Row(
        Modifier.fillMaxWidth()
            .padding(vertical = 5.dp)
            .height(35.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Button(
            modifier = Modifier.size(35.dp),
            colors = ButtonColors(
                containerColor = colorResource(R.color.purple_200),
                contentColor = Color.White,
                disabledContentColor = colorResource(R.color.purple_500),
                disabledContainerColor = colorResource(R.color.purple_500),
            ),
            shape = CircleShape,
            contentPadding = PaddingValues(5.dp),
            onClick = onLetterClick
        ) {
            Text(mapData.letterParagraph.letter)
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
                colors = if(mapData == selectedMap)
                    ButtonDefaults.buttonColors(containerColor = Color.LightGray )
                else
                    ButtonDefaults.buttonColors(containerColor = Color.Transparent ),
                onClick = onMapClick
            ) {
                mapData.number?.let {
                    Text(
                        text = it.toString(),
                        color = Color.Gray
                    )
                }
            }
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(size = 4.dp))
                    .background(Color.Gray)
            )
        }
    }
}

@Composable
fun TitleButton(
    onClick: () -> Unit = {},
    text: String = "Text",
    enabled: Boolean = true,
    highlighted: Boolean = false,
    colors: ButtonColors = ButtonColors(Color.White, Color.Gray, Color.Gray, Color.Gray),
    modifier: Modifier = Modifier
)
{
    Button(
        onClick = onClick,
        colors = if(!highlighted) colors else ButtonDefaults.buttonColors(colorResource(R.color.button_blue), Color.White),
        border = BorderStroke(2.dp, color = if(!highlighted) Color.Gray else colorResource(R.color.button_blue_border)),
        shape = RoundedCornerShape(30),
        enabled = enabled,
        contentPadding = PaddingValues(8.dp),
        modifier = modifier
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


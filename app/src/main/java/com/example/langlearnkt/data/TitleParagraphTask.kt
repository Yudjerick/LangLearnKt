package com.example.langlearnkt.data

data class TitleParagraphTask(
    val paragraphs: List<ParagraphData>
)

data class ParagraphData(
    val text: String,
    val letter: String,
    val title: String
)
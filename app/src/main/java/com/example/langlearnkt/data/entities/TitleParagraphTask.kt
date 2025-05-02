package com.example.langlearnkt.data.entities

data class TitleParagraphTask(
    val paragraphs: List<ParagraphData>
): Task()

data class ParagraphData(
    val text: String,
    val letter: String,
    val title: String
)
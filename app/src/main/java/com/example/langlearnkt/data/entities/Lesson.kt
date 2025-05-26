package com.example.langlearnkt.data.entities

import com.google.firebase.firestore.DocumentId

data class Lesson(
    val metaData: LessonMetaData,
    val content: LessonContent
)

data class LessonMetaData(
    @DocumentId
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
)

data class LessonContent(
    val id: String,
    val tasks: List<Task>
)
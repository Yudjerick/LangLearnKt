package com.example.langlearnkt.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jspecify.annotations.NonNull

@Entity(tableName = "lesson_result", primaryKeys = ["userId","lessonId"])
data class LessonResult(
    val userId: String = "",
    val lessonId: String = "",
    val result: Float? = null
)
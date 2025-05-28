package com.example.langlearnkt.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.langlearnkt.data.converters.RoomTypeConverters
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

@Entity(tableName = "lesson")
data class RoomLesson(
    @PrimaryKey
    val id: String,
    val title: String?,
    val description: String?,
    @TypeConverters(*[RoomTypeConverters::class])
    val tasks: List<Task>?
){
    companion object{
        fun toRoomLesson(lesson: Lesson):RoomLesson{
            return RoomLesson(
                lesson.metaData.id!!,
                lesson.metaData.title,
                lesson.metaData.description,
                lesson.content.tasks
            )
        }

        fun toLesson(roomLesson: RoomLesson?): Lesson?{

            if (roomLesson != null) {
                return Lesson(
                    LessonMetaData(
                        roomLesson.id,
                        roomLesson.title,
                        roomLesson.description
                    ),
                    LessonContent(
                        roomLesson.id,
                        roomLesson.tasks!!
                    )
                )
            }
            else{
                return null
            }
        }
    }
}
package com.example.langlearnkt.data.localcache

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.TypeConverters
import com.example.langlearnkt.data.converters.RoomTypeConverters
import com.example.langlearnkt.data.entities.Lesson
import com.example.langlearnkt.data.entities.LessonContent
import com.example.langlearnkt.data.entities.LessonMetaData
import com.example.langlearnkt.data.entities.Task

@Dao
interface Dao {
    @Query("SELECT * FROM lesson WHERE id = :id LIMIT 1")
    suspend fun getLesson(id: String): RoomLesson?

    @Insert
    suspend fun insertLesson(lesson: RoomLesson)
}

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
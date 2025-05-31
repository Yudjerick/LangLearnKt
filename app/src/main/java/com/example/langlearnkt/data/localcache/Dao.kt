package com.example.langlearnkt.data.localcache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.TypeConverters
import com.example.langlearnkt.data.converters.RoomTypeConverters
import com.example.langlearnkt.data.entities.Lesson
import com.example.langlearnkt.data.entities.LessonContent
import com.example.langlearnkt.data.entities.LessonMetaData
import com.example.langlearnkt.data.entities.LessonResult
import com.example.langlearnkt.data.entities.RoomLesson
import com.example.langlearnkt.data.entities.Task

@Dao
interface Dao {
    @Query("SELECT * FROM lesson WHERE id = :id LIMIT 1")
    suspend fun getLesson(id: String): RoomLesson?

    @Insert
    suspend fun insertLesson(lesson: RoomLesson)

    @Query("SELECT * FROM lesson LIMIT :limit")
    suspend fun getAllLessons(limit: Int): List<RoomLesson>

    @Query("SELECT * FROM lesson_result WHERE lessonId = :lessonId AND userId = :userId LIMIT 1")
    suspend fun getLessonResult(lessonId: String, userId: String): LessonResult?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessonResult(lessonResult: LessonResult)

    @Query("DELETE FROM lesson_result WHERE userId = :userId")
    suspend fun deleteLessonResultByUser(userId: String)
}


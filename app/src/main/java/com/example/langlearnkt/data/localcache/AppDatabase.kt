package com.example.langlearnkt.data.localcache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.langlearnkt.data.converters.RoomTypeConverters
import com.example.langlearnkt.data.entities.Lesson
import com.example.langlearnkt.data.entities.LessonResult
import com.example.langlearnkt.data.entities.RoomLesson

@Database(entities = [RoomLesson::class, LessonResult::class], version = 1)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dao(): Dao

    companion object{
        lateinit var instance: AppDatabase
    }
}
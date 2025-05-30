package com.example.langlearnkt.data.repositories

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.langlearnkt.data.entities.LessonResult
import com.example.langlearnkt.data.entities.RoomLesson
import com.example.langlearnkt.data.localcache.AppDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class LessonResultRepository {
    suspend fun saveResult(userId: String, lessonId: String, result: Float){
        FirebaseFirestore.getInstance().collection("lesson_results")
            .document("${userId}:${lessonId}")
            .set(LessonResult(userId, lessonId, result))
        try {
            saveResultToCache(LessonResult(userId,lessonId,result))
        }
        catch (e: Exception){
            Log.e("AAA", e.message.toString())
        }

    }

    suspend fun getResult(userId: String, lessonId: String): Float?{
        try {
            val cachedResult = getResultFromCache(userId, lessonId)
            Log.e("AAA", cachedResult.toString())
            if(cachedResult != null){
                return cachedResult.result
            }

        }
        catch (e: Exception){
            Log.e("AAA", e.message.toString())
        }
        return try {
            FirebaseFirestore.getInstance().collection("lesson_results")
                .document("${userId}:${lessonId}")
                .get().await().toObject<LessonResult>()?.result
        }
        catch (e: Exception){
            Log.e("Firestore", e.message.toString())
            null
        }

    }

    suspend fun deleteResultsForUser(userId: String){
        AppDatabase.instance.dao().deleteLessonResultByUser(userId)
        val firestore = FirebaseFirestore.getInstance()
        val results = firestore.collection("lesson_results")
            .whereEqualTo("userId", userId)
            .get()
            .await()

        val batch = firestore.batch()
        for (document in results) {
            batch.delete(document.reference)
        }

        batch.commit().await()
    }

    private suspend fun getResultFromCache(userId: String, lessonId: String): LessonResult?{
        return AppDatabase.instance.dao().getLessonResult(lessonId, userId)
    }

    private suspend fun saveResultToCache(result: LessonResult){
        AppDatabase.instance.dao().insertLessonResult(result)
    }


}


package com.example.langlearnkt.data.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class LessonResultRepository {
    fun saveResult(userId: String, lessonId: String, result: Float){
        FirebaseFirestore.getInstance().collection("lesson_results")
            .document("${userId}:${lessonId}")
            .set(LessonResult(userId, lessonId, result))
    }

    suspend fun getResult(userId: String, lessonId: String): Float?{
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
}

data class LessonResult(
    val userId: String? = null,
    val lessonId: String? = null,
    val result: Float? = null
)
package com.example.langlearnkt.data.repositories

import android.util.Log
import com.example.langlearnkt.data.converters.Converter
import com.example.langlearnkt.data.entities.Lesson
import com.example.langlearnkt.data.entities.LessonContent
import com.example.langlearnkt.data.entities.LessonMetaData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class LessonRepository(){
    suspend fun getLessonsMetaDataList(): List<LessonMetaData> {
        return try {
            FirebaseFirestore.getInstance()
                .collection("lessons_meta")
                .get()
                .await()
                .documents
                .mapNotNull { document -> document.toObject<LessonMetaData>() }
        } catch (e: Exception) {
            Log.e("Firestore", "Ошибка загрузки уроков", e)
            emptyList()
        }
    }

    suspend fun getLesson(metaData: LessonMetaData): Lesson? {
        return try {
            val contentDoc = FirebaseFirestore.getInstance()
                .collection("lessons_content").document(metaData.id!!).get()
                .await().data
            val tasks = contentDoc?.get("tasks") as? List<Map<String, Any>> ?: emptyList()
            val id = contentDoc?.get("id") as String
            Lesson(
                metaData,
                LessonContent(
                    id = id,
                    tasks = tasks.mapNotNull { Converter().mapToTask(it) }
                )
            )
        }
        catch (e: Exception) {
            e.message?.let { Log.e("AAA", it) }
            null
        }
    }

    suspend fun saveLesson(lesson: Lesson) {
        try {
            val metaDoc = Firebase.firestore.collection("lessons_meta").document()
            metaDoc.set(
                lesson.metaData
            ).addOnSuccessListener {
                val contentDoc = Firebase.firestore
                    .collection("lessons_content").document(metaDoc.id)

                contentDoc.set(
                    mapOf(
                        "id" to metaDoc.id,
                        "tasks" to lesson.content.tasks.map { task ->
                            Converter().taskToMap(task)
                        }
                    )
                )
            }
        } catch (e: Exception) {
            e.message?.let { Log.e("AAA", it) }
        }
    }
}


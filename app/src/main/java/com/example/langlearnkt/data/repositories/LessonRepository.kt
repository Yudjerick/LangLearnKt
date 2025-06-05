package com.example.langlearnkt.data.repositories

import android.util.Log
import com.example.langlearnkt.data.converters.FirestoreConverter
import com.example.langlearnkt.data.entities.Lesson
import com.example.langlearnkt.data.entities.LessonContent
import com.example.langlearnkt.data.entities.LessonMetaData
import com.example.langlearnkt.data.entities.RoomLesson
import com.example.langlearnkt.data.localcache.AppDatabase
import com.example.langlearnkt.data.localcache.Dao
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class LessonRepository(){
    suspend fun getLessonsMetaDataList(): List<LessonMetaData> {
        /*try {
            val cachedMetadata = AppDatabase.instance.dao().getAllLessons(100).map {
                x -> LessonMetaData(x.id, x.title, x.description)
            }
            if (cachedMetadata.isNotEmpty()){
                return cachedMetadata
            }
        }
        catch (e: Exception){
            Log.e("AAA", e.message.toString())
        }*/
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

    suspend fun getFreshLessonsMetaDataList(): List<LessonMetaData> {
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

    suspend fun getLesson(lessonId: String): Lesson? {
        try {
            val cachedLesson = getLessonFromCache(lessonId)
            if (cachedLesson != null){
                return cachedLesson
            }
        }
        catch (e: Exception){
            Log.e("AAA", e.message.toString() )
        }

        return try {
            val firestore = FirebaseFirestore.getInstance()
            val contentDoc = firestore
                .collection("lessons_content").document(lessonId).get()
                .await().data
            val metadataDoc = firestore.collection("lessons_meta")
                .document(lessonId).get().
                await().data
            val tasks = contentDoc?.get("tasks") as? List<Map<String, Any>> ?: emptyList()
            val id = contentDoc?.get("id") as String
            val description = metadataDoc?.get("description") as String
            val title = metadataDoc.get("title") as String
            val result = Lesson(
                LessonMetaData(id, title, description),
                LessonContent(
                    id = id,
                    tasks = tasks.mapNotNull { FirestoreConverter().mapToTask(it) }
                )
            )
            saveLessonInCache(result)
            result
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
                            FirestoreConverter().taskToMap(task)
                        }
                    )
                )
            }
        } catch (e: Exception) {
            e.message?.let { Log.e("AAA", it) }
        }
    }

    private suspend fun getLessonFromCache(lessonId: String): Lesson?{
        return RoomLesson.toLesson(AppDatabase.instance.dao().getLesson(lessonId))
    }

    private suspend fun saveLessonInCache(lesson: Lesson){
        AppDatabase.instance.dao().insertLesson(RoomLesson.toRoomLesson(lesson))
    }
}


package com.example.langlearnkt.data.converters

import androidx.room.TypeConverter
import com.example.langlearnkt.data.entities.OrderTask
import com.example.langlearnkt.data.entities.ParagraphData
import com.example.langlearnkt.data.entities.Task
import com.example.langlearnkt.data.entities.TitleParagraphTask
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class FirestoreConverter {

    companion object {
        const val TYPE_ORDER_TASK = "order_task"
        const val TYPE_TITLE_PARAGRAPH_TASK = "title_paragraph_task"
    }

    fun taskToMap(task: Task): Map<String, Any> {
        return when (task) {
            is OrderTask -> mapOf(
                "type" to TYPE_ORDER_TASK,
                "title" to task.title,
                "text" to task.text,
                "answer" to task.answer,
                "additionalVariants" to task.additionalVariants
            )
            is TitleParagraphTask -> mapOf(
                "type" to TYPE_TITLE_PARAGRAPH_TASK,
                "paragraphs" to task.paragraphs.map { paragraph ->
                    mapOf(
                        "text" to paragraph.text,
                        "letter" to paragraph.letter,
                        "title" to paragraph.title
                    )
                }
            )
        }
    }

    fun mapToTask(map: Map<String, Any>): Task {
        return when (map["type"]) {
            TYPE_ORDER_TASK -> OrderTask(
                title = map["title"] as String,
                text = map["text"] as String,
                answer = map["answer"] as List<String>,
                additionalVariants = map["additionalVariants"] as List<String>
            )
            TYPE_TITLE_PARAGRAPH_TASK -> TitleParagraphTask(
                paragraphs = (map["paragraphs"] as List<Map<String, String>>).map { paragraphMap ->
                    ParagraphData(
                        text = paragraphMap["text"] as String,
                        letter = paragraphMap["letter"] as String,
                        title = paragraphMap["title"] as String
                    )
                }
            )
            else -> throw IllegalArgumentException("Unknown task type")
        }
    }

}

class RoomTypeConverters{
    @TypeConverter
    fun tasksListToString(tasks: List<Task>?): String{
        if (tasks == null){
            return ""
        }
        val mapConverter = FirestoreConverter()
        val taskMaps = mutableListOf<Map<String,Any>>()
        tasks.forEach{
            t -> taskMaps.add(mapConverter.taskToMap(t))
        }
        val result = GsonBuilder().create().toJson(taskMaps)
        return result
    }

    @TypeConverter
    fun stringToTasksList(string: String): List<Task>? {
        if (string.isEmpty()) {
            return null
        }
        try {
            val mapConverter = FirestoreConverter()
            val type = object : TypeToken<List<Map<String, Any>>>() {}.type
            val taskMaps: List<Map<String, Any>> = GsonBuilder().create().fromJson(string, type)
            return taskMaps.map { mapConverter.mapToTask(it) }
        } catch (e: Exception) {
            // Логируем ошибку или выбрасываем исключение в зависимости от требований
            throw IllegalArgumentException("Failed to parse tasks list from JSON", e)
        }
    }
}
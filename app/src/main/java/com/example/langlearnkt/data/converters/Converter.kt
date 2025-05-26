package com.example.langlearnkt.data.converters

import com.example.langlearnkt.data.entities.OrderTask
import com.example.langlearnkt.data.entities.ParagraphData
import com.example.langlearnkt.data.entities.Task
import com.example.langlearnkt.data.entities.TitleParagraphTask

class Converter {

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
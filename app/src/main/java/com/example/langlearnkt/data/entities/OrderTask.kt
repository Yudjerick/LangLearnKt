package com.example.langlearnkt.data.entities

data class OrderTask(
    val title: String,
    val text: String,
    val answer: List<String>,
    val additionalVariants: List<String>
) : Task()
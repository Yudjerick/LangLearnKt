package com.example.langlearnkt.data

data class OrderTask(
    val id: Int,
    val title: String,
    val text: String,
    val answer: List<String>,
    val additionalVariants: List<String>
) {

}
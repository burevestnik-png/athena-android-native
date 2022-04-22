package ru.yofik.athena.chat.domain.model

data class UiMessage(
    val id: Long,
    val sender: String,
    val content: String,
    val time: String
)
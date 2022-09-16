package ru.yofik.athena.chatlist.domain.model

data class UiChat(
    val id: Long,
    val name: String,
    val message: UiMessage,
    val isSelected: Boolean = false
)

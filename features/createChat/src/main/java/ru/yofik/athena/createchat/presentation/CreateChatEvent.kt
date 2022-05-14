package ru.yofik.athena.createchat.presentation

sealed class CreateChatEvent {
    object GetAllUsers : CreateChatEvent()
    object RequestGetAllUsers : CreateChatEvent()
    data class CreateChat(val id: Long, val name: String) : CreateChatEvent()
}

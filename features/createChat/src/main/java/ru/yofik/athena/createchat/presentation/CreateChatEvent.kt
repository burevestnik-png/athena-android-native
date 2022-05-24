package ru.yofik.athena.createchat.presentation

sealed class CreateChatEvent {
    object RequestMoreUsers : CreateChatEvent()
    data class CreateChat(val id: Long, val name: String) : CreateChatEvent()
}

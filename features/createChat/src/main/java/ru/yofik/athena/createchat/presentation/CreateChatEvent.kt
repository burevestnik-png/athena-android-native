package ru.yofik.athena.createchat.presentation

sealed class CreateChatEvent {
    object RequestMoreUsers : CreateChatEvent()
    object ForceRequestAllUsers : CreateChatEvent()
    data class CreateChat(val targetUserId: Long) : CreateChatEvent()
}

package ru.yofik.athena.chat.presentation

sealed class ChatFragmentEvent {
    data class GetChatInfo(val id: Long) : ChatFragmentEvent()
    data class UpdateInput(val content: String) : ChatFragmentEvent()
    object SendMessage : ChatFragmentEvent()
}

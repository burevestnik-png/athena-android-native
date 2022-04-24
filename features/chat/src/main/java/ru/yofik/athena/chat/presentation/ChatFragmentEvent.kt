package ru.yofik.athena.chat.presentation

sealed class ChatFragmentEvent {
    data class GetChat(val id: Long) : ChatFragmentEvent()
    data class UpdateInput(val content: String) : ChatFragmentEvent()
    object SendMessage : ChatFragmentEvent()
}

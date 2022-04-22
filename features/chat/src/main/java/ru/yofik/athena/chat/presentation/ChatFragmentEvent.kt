package ru.yofik.athena.chat.presentation

sealed class ChatFragmentEvent {
    data class GetChat(val id: Long) : ChatFragmentEvent()
    data class SendMessage(val content: String) : ChatFragmentEvent()
}

package ru.yofik.athena.chat.presentation

sealed class ChatFragmentEvent {
    object GetAllMessages : ChatFragmentEvent()
    data class SendMessage(val content: String) : ChatFragmentEvent()
    data class SetChatId(val id: Long) : ChatFragmentEvent()
}

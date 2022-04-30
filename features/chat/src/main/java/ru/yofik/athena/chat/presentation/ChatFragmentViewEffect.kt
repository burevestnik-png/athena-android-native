package ru.yofik.athena.chat.presentation

sealed class ChatFragmentViewEffect {
    data class SetChatName(val name: String) : ChatFragmentViewEffect()
    object ClearInput : ChatFragmentViewEffect()
}

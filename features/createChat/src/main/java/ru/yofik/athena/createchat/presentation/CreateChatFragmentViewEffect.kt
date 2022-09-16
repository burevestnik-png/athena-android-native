package ru.yofik.athena.createchat.presentation

sealed class CreateChatFragmentViewEffect {
    object NavigateToChatListScreen : CreateChatFragmentViewEffect()
}

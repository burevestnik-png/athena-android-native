package ru.yofik.athena.chatList.presentation

sealed class ChatListEvent {
    object GetAllChats : ChatListEvent()
}
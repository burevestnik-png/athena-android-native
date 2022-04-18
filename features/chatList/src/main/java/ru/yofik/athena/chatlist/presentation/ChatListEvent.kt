package ru.yofik.athena.chatlist.presentation

sealed class ChatListEvent {
    object GetAllChats : ChatListEvent()
}
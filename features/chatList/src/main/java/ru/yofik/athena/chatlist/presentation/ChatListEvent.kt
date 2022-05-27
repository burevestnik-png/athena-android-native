package ru.yofik.athena.chatlist.presentation

sealed class ChatListEvent {
    object ForceGetAllChats : ChatListEvent()
    object RequestNextChatsPage : ChatListEvent()
}
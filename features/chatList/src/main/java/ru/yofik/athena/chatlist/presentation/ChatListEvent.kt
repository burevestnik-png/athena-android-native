package ru.yofik.athena.chatlist.presentation

sealed class ChatListEvent {
    object ForceGetAllChats : ChatListEvent()
    object RequestNextChatsPage : ChatListEvent()

    data class AddChatToSelection(val chatId: Long) : ChatListEvent()
    object CancelSelection : ChatListEvent()
}

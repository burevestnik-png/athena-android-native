package ru.yofik.athena.chat.domain.model.mappers

import ru.yofik.athena.chat.domain.model.UiChat
import ru.yofik.athena.common.domain.model.chat.ChatWithDetails
import ru.yofik.athena.common.presentation.model.UiMapper
import javax.inject.Inject

class UiChatMapper @Inject constructor() : UiMapper<Pair<ChatWithDetails, Long>, UiChat> {
    override fun mapToView(model: Pair<ChatWithDetails, Long>): UiChat {
        val (chat, chatHolderId) = model
        return UiChat(
            id = chat.id,
            users = chat.users,
            name = chat.name,
            chatHolderId = chatHolderId
        )
    }
}
package ru.yofik.athena.chat.domain.model.mappers

import javax.inject.Inject
import ru.yofik.athena.chat.domain.model.UiChat
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.presentation.model.UiMapper

class UiChatMapper @Inject constructor() : UiMapper<Pair<Chat, Long>, UiChat> {
    override fun mapToView(model: Pair<Chat, Long>): UiChat {
        val (chat, chatHolderId) = model
        return UiChat(
            id = chat.id,
            users = chat.users,
            name = chat.name,
            chatHolderId = chatHolderId
        )
    }
}

package ru.yofik.athena.chatlist.domain.model.mappers

import ru.yofik.athena.chatlist.domain.model.UiChat
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.presentation.model.UiMapper
import javax.inject.Inject

class UiChatMapper @Inject constructor(private val uiMessageMapper: UiMessageMapper) :
    UiMapper<Chat, UiChat> {
    override fun mapToView(model: Chat): UiChat {
        return UiChat(
            id = model.id,
            name = model.name,
            message = uiMessageMapper.mapToView(model.lastMessage)
        )
    }
}

package ru.yofik.athena.chat.domain.model.mappers

import ru.yofik.athena.chat.domain.model.UiChat
import ru.yofik.athena.chat.domain.model.UiMessageSenderType
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.presentation.model.UiMapper
import javax.inject.Inject

class UiMessageSenderTypeMapper @Inject constructor() :
    UiMapper<Pair<Message, UiChat>, UiMessageSenderType> {
    override fun mapToView(model: Pair<Message, UiChat>): UiMessageSenderType {
        val (message, chat) = model
        return if (message.senderId == chat.chatHolderId) UiMessageSenderType.OWNER else UiMessageSenderType.NOT_OWNER
    }
}
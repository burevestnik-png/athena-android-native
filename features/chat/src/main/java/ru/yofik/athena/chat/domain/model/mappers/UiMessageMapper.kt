package ru.yofik.athena.chat.domain.model.mappers

import javax.inject.Inject
import ru.yofik.athena.chat.domain.model.UiChat
import ru.yofik.athena.chat.domain.model.UiMessage
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.presentation.model.UiMapper

class UiMessageMapper
@Inject
constructor(
    private val uiSenderMapper: UiSenderMapper,
    private val uiMessageSenderTypeMapper: UiMessageSenderTypeMapper
) : UiMapper<Pair<Message, UiChat>, UiMessage> {
    override fun mapToView(model: Pair<Message, UiChat>): UiMessage {
        val (message) = model

        return UiMessage(
            id = message.id,
            sender = uiSenderMapper.mapToView(model),
            content = message.content,
            dateTime = message.creationDate,
            senderType = uiMessageSenderTypeMapper.mapToView(model)
        )
    }
}

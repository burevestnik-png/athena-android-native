package ru.yofik.athena.chat.domain.model.mappers

import ru.yofik.athena.chat.domain.model.UiChat
import ru.yofik.athena.chat.domain.model.UiMessage
import javax.inject.Inject
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.presentation.model.UiMapper
import ru.yofik.athena.common.utils.toFormattedString
import timber.log.Timber

class UiMessageMapper @Inject constructor(
    private val uiSenderMapper: UiSenderMapper,
    private val uiMessageSenderTypeMapper: UiMessageSenderTypeMapper
) : UiMapper<Pair<Message, UiChat>, UiMessage> {
    override fun mapToView(model: Pair<Message, UiChat>): UiMessage {
        val (message) = model

        return UiMessage(
            id = message.id,
            sender = uiSenderMapper.mapToView(model),
            content = message.content,
            time = message.dateTime.toFormattedString(),
            senderType = uiMessageSenderTypeMapper.mapToView(model)
        )
    }
}

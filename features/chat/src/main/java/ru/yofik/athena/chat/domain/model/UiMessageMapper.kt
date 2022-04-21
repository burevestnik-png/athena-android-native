package ru.yofik.athena.chat.domain.model

import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.presentation.model.UiMapper
import ru.yofik.athena.common.utils.toFormattedString

class UiMessageMapper : UiMapper<Message, UiMessage> {
    override fun mapToView(model: Message): UiMessage {
        return UiMessage(
            id = model.id,
            content = model.content,
            time = model.dateTime.toFormattedString()
        )
    }
}
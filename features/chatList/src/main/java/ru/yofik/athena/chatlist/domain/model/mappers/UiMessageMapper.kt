package ru.yofik.athena.chatlist.domain.model.mappers

import javax.inject.Inject
import ru.yofik.athena.chatlist.domain.model.UiMessage
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.presentation.model.UiMapper
import ru.yofik.athena.common.utils.toFormattedString

class UiMessageMapper @Inject constructor() : UiMapper<Message, UiMessage> {
    override fun mapToView(model: Message): UiMessage {
        return UiMessage(
            content = model.content,
            time = model.creationDate.toFormattedString()
        )
    }
}

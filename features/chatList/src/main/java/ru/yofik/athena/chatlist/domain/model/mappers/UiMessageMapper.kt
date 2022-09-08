package ru.yofik.athena.chatlist.domain.model.mappers

import ru.yofik.athena.chatlist.domain.model.UiMessage
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.presentation.model.UiMapper
import ru.yofik.athena.common.utils.TimeUtils
import javax.inject.Inject

class UiMessageMapper @Inject constructor() : UiMapper<Message, UiMessage> {
    override fun mapToView(model: Message): UiMessage {
        return UiMessage(
            content = model.content,
            time = TimeUtils.localDateTimeToString(model.creationDate, TimeUtils.SHORT_TIME_FORMAT)
        )
    }
}

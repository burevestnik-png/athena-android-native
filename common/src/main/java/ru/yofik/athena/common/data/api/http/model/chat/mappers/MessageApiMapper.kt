package ru.yofik.athena.common.data.api.http.model.chat.mappers

import javax.inject.Inject
import ru.yofik.athena.common.data.api.http.model.chat.responses.dto.MessageDto
import ru.yofik.athena.common.data.api.mapping.ApiMapper
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.utils.TimeUtils

class MessageApiMapper @Inject constructor() : ApiMapper<MessageDto, Message> {
    override fun mapToDomain(entityDTO: MessageDto?): Message {
        return Message(
            id = entityDTO?.id ?: Message.NULLABLE_MESSAGE_ID,
            content = entityDTO?.text.orEmpty(),
            senderId = entityDTO?.senderId ?: -1,
            chatId = entityDTO?.chatId ?: -1,
            creationDate = TimeUtils.parseToLocalDateTime(entityDTO?.creationDate ?: ""),
            modificationDate = TimeUtils.parseToLocalDateTime(entityDTO?.modificationDate ?: "")
        )
    }
}

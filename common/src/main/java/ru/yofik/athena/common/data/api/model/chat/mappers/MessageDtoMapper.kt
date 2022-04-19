package ru.yofik.athena.common.data.api.model.chat.mappers

import javax.inject.Inject
import ru.yofik.athena.common.data.api.model.chat.responses.dto.MessageDto
import ru.yofik.athena.common.data.api.model.mappers.DtoMapper
import ru.yofik.athena.common.data.api.model.mappers.MappingException
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.utils.TimeUtils

class MessageDtoMapper @Inject constructor() : DtoMapper<MessageDto, Message> {
    override fun mapToDomain(entityDTO: MessageDto?): Message {
        return Message(
            id = entityDTO?.id ?: Message.NULLABLE_MESSAGE_ID,
            content = entityDTO?.text.orEmpty(),
            senderId = entityDTO?.senderId ?: -1,
            chatId = entityDTO?.chatId ?: -1,
            dateTime = TimeUtils.parseToLocalDateTime(entityDTO?.date ?: "")
        )
    }
}

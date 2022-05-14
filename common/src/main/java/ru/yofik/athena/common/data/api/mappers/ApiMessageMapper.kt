package ru.yofik.athena.common.data.api.mappers

import javax.inject.Inject
import ru.yofik.athena.common.data.api.mappers.dto.ApiMessage
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.utils.TimeUtils

class ApiMessageMapper @Inject constructor() : ApiMapper<ApiMessage, Message> {
    override fun mapToDomain(apiEntity: ApiMessage?): Message {
        // todo delete
        if (apiEntity == null) return Message.nullable()
        return Message(
            id = apiEntity?.id ?: throw MappingException("Message id cannot be null"),
            content = apiEntity.text.orEmpty(),
            senderId = apiEntity.senderId ?: throw MappingException("Sender id cannot be null"),
            chatId = apiEntity.chatId ?: throw MappingException("Chat id cannot be null"),
            creationDate = TimeUtils.parseToLocalDateTime(apiEntity.creationDate),
            modificationDate = TimeUtils.parseToLocalDateTime(apiEntity.modificationDate)
        )
    }
}

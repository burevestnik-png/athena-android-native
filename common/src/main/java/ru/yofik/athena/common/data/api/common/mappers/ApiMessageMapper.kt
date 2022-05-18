package ru.yofik.athena.common.data.api.common.mappers

import ru.yofik.athena.common.data.api.common.ApiMapper
import ru.yofik.athena.common.data.api.common.MappingException
import ru.yofik.athena.common.data.api.common.apiEntity.ApiMessage
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.utils.TimeUtils
import javax.inject.Inject

class ApiMessageMapper @Inject constructor() : ApiMapper<ApiMessage, Message> {
    override fun mapToDomain(apiEntity: ApiMessage?): Message {
        if (apiEntity == null) return Message.nullable()

        return Message(
            id = apiEntity.id ?: throw MappingException("Message id cannot be null"),
            content = apiEntity.text.orEmpty(),
            senderId = apiEntity.senderId ?: throw MappingException("Sender id cannot be null"),
            chatId = apiEntity.chatId ?: throw MappingException("Chat id cannot be null"),
            creationDate = TimeUtils.parseToLocalDateTime(apiEntity.creationDate),
            modificationDate = TimeUtils.parseToLocalDateTime(apiEntity.modificationDate)
        )
    }
}

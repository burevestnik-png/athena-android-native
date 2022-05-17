package ru.yofik.athena.common.data.api.ws.model.mappers

import com.squareup.moshi.Moshi
import javax.inject.Inject
import ru.yofik.athena.common.data.api.mappers.ApiMapper
import ru.yofik.athena.common.data.api.mappers.MappingException
import ru.yofik.athena.common.data.api.ws.model.messages.input.RawDeleteMessage
import ru.yofik.athena.common.domain.model.notification.DeleteMessageNotification

class ApiDeleteMessageMapper @Inject constructor() : ApiMapper<String, DeleteMessageNotification> {
    private val adapter = Moshi.Builder().build().adapter(RawDeleteMessage::class.java)

    override fun mapToDomain(apiEntity: String?): DeleteMessageNotification {
        if (apiEntity == null) throw MappingException("Message cannot be null")

        val wsMessage =
            adapter.fromJson(apiEntity) ?: throw MappingException("Delete msg parse error")
        return DeleteMessageNotification(
            chatId = wsMessage.argument.chatId,
            ids = wsMessage.argument.messages
        )
    }
}

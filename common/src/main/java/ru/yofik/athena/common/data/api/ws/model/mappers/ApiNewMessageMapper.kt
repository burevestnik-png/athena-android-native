package ru.yofik.athena.common.data.api.ws.model.mappers

import com.squareup.moshi.Moshi
import javax.inject.Inject
import ru.yofik.athena.common.data.api.mappers.ApiMapper
import ru.yofik.athena.common.data.api.mappers.ApiMessageMapper
import ru.yofik.athena.common.data.api.mappers.MappingException
import ru.yofik.athena.common.data.api.ws.model.messages.input.RawNewMessage
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification

class ApiNewMessageMapper @Inject constructor(private val apiMessageMapper: ApiMessageMapper) :
    ApiMapper<String, NewMessageNotification> {
    private val adapter = Moshi.Builder().build().adapter(RawNewMessage::class.java)

    override fun mapToDomain(apiEntity: String?): NewMessageNotification {
        if (apiEntity == null) throw MappingException("Message cannot be null")

        val wsMessage =
            adapter.fromJson(apiEntity) ?: throw MappingException("Delete msg parse error")

        return NewMessageNotification(
            message = apiMessageMapper.mapToDomain(wsMessage.argument.message)
        )
    }
}
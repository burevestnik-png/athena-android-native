package ru.yofik.athena.common.data.api.ws.model.mappers

import com.squareup.moshi.Moshi
import ru.yofik.athena.common.data.api.common.ApiMapper
import ru.yofik.athena.common.data.api.common.MappingException
import ru.yofik.athena.common.data.api.common.mappers.ApiMessageMapper
import ru.yofik.athena.common.data.api.ws.model.messages.input.RawUpdateMessage
import ru.yofik.athena.common.domain.model.notification.UpdateMessageNotification
import javax.inject.Inject

internal class ApiUpdateMessageMapper @Inject constructor(private val apiMessageMapper: ApiMessageMapper) :
    ApiMapper<String, UpdateMessageNotification> {
    private val adapter = Moshi.Builder().build().adapter(RawUpdateMessage::class.java)

    override fun mapToDomain(apiEntity: String?): UpdateMessageNotification {
        if (apiEntity == null) throw MappingException("Message cannot be null")

        val wsMessage =
            adapter.fromJson(apiEntity) ?: throw MappingException("Delete msg parse error")

        return UpdateMessageNotification(
            message = apiMessageMapper.mapToDomain(wsMessage.argument.message)
        )
    }
}

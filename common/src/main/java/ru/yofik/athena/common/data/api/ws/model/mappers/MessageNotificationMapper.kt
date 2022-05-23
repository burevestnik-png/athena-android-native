package ru.yofik.athena.common.data.api.ws.model.mappers

import com.squareup.moshi.Moshi
import ru.yofik.athena.common.data.api.common.ApiMapper
import ru.yofik.athena.common.data.api.common.MappingException
import ru.yofik.athena.common.data.api.ws.model.ArgumentType
import ru.yofik.athena.common.data.api.ws.model.CommandType
import ru.yofik.athena.common.data.api.ws.model.WsMessage
import ru.yofik.athena.common.data.api.ws.model.WsMessageWithArgument
import ru.yofik.athena.common.domain.model.notification.Notification
import javax.inject.Inject

internal class MessageNotificationMapper
@Inject
constructor(
    private val apiDeleteMessageMapper: ApiDeleteMessageMapper,
    private val apiNewMessageMapper: ApiNewMessageMapper,
    private val apiUpdateMessageMapper: ApiUpdateMessageMapper
) : ApiMapper<String?, Notification> {
    private val moshi = Moshi.Builder().build()

    private val messageAdapter = moshi.adapter(WsMessage::class.java)
    private val messageWithArgumentAdapter = moshi.adapter(WsMessageWithArgument::class.java)

    override fun mapToDomain(apiEntity: String?): Notification {
        if (apiEntity.isNullOrEmpty()) throw MappingException("Invalid data from server!")

        val command: WsMessage =
            messageAdapter.fromJson(apiEntity) ?: throw MappingException("Parse error")

        return when (command.type) {
            CommandType.RECEIVE_NOTIFICATION -> parseReceiveNotificationCommand(apiEntity)
            else -> throw MappingException("Unsupported type ${command.type}")
        }
    }

    private fun parseReceiveNotificationCommand(json: String): Notification {
        val messageWithArgument: WsMessageWithArgument =
            messageWithArgumentAdapter.fromJson(json) ?: throw MappingException("Parse error")

        return when (messageWithArgument.argument.type) {
            ArgumentType.NEW_MESSAGE -> apiNewMessageMapper.mapToDomain(json)
            ArgumentType.DELETED_MESSAGES -> apiDeleteMessageMapper.mapToDomain(json)
            ArgumentType.UPDATED_MESSAGE -> apiUpdateMessageMapper.mapToDomain(json)
        }
    }
}

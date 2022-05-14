package ru.yofik.athena.common.data.api.ws.model.mappers

import com.squareup.moshi.Moshi
import javax.inject.Inject
import ru.yofik.athena.common.data.api.mappers.ApiMessageMapper
import ru.yofik.athena.common.data.api.mappers.ApiMapper
import ru.yofik.athena.common.data.api.mappers.MappingException
import ru.yofik.athena.common.data.api.ws.model.ArgumentType
import ru.yofik.athena.common.data.api.ws.model.CommandType
import ru.yofik.athena.common.data.api.ws.model.WsMessage
import ru.yofik.athena.common.data.api.ws.model.WsMessageWithArgument
import ru.yofik.athena.common.data.api.ws.model.messages.RawDeleteMessage
import ru.yofik.athena.common.data.api.ws.model.messages.RawNewMessage
import ru.yofik.athena.common.data.api.ws.model.messages.RawUpdateMessage
import ru.yofik.athena.common.domain.model.notification.DeleteMessageNotification
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import ru.yofik.athena.common.domain.model.notification.Notification
import ru.yofik.athena.common.domain.model.notification.UpdateMessageNotification

class MessageNotificationMapper
@Inject
constructor(private val messageApiMapper: ApiMessageMapper) : ApiMapper<String?, Notification> {
    private val moshi = Moshi.Builder().build()

    private val messageAdapter = moshi.adapter(WsMessage::class.java)
    private val messageWithArgumentAdapter = moshi.adapter(WsMessageWithArgument::class.java)

    private val newMessageAdapter = moshi.adapter(RawNewMessage::class.java)
    private val updateMessageAdapter = moshi.adapter(RawUpdateMessage::class.java)
    private val deleteMessageAdapter = moshi.adapter(RawDeleteMessage::class.java)

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
            ArgumentType.NEW_MESSAGE -> parseNewMessageNotification(json)
            ArgumentType.DELETED_MESSAGES -> parseDeletedMessagesNotification(json)
            ArgumentType.UPDATED_MESSAGE -> parseUpdatedMessagesNotification(json)
        }
    }

    private fun parseUpdatedMessagesNotification(json: String): UpdateMessageNotification {
        val wsMessage = updateMessageAdapter.fromJson(json)?: throw MappingException("Parse error")
        return UpdateMessageNotification(messageApiMapper.mapToDomain(wsMessage.argument.message))
    }

    private fun parseDeletedMessagesNotification(json: String): DeleteMessageNotification {
        val wsMessage = deleteMessageAdapter.fromJson(json)?: throw MappingException("Parse error")
        return DeleteMessageNotification(wsMessage.argument.messages)
    }

    private fun parseNewMessageNotification(json: String): NewMessageNotification {
        val wsMessage = newMessageAdapter.fromJson(json) ?: throw MappingException("Parse error")
        return NewMessageNotification(messageApiMapper.mapToDomain(wsMessage.argument.message))
    }
}

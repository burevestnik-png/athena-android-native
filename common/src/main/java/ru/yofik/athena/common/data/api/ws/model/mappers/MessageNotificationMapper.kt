package ru.yofik.athena.common.data.api.ws.model.mappers

import com.squareup.moshi.Moshi
import javax.inject.Inject
import ru.yofik.athena.common.data.api.http.model.chat.mappers.MessageApiMapper
import ru.yofik.athena.common.data.api.mapping.ApiMapper
import ru.yofik.athena.common.data.api.mapping.MappingException
import ru.yofik.athena.common.data.api.ws.model.ArgumentType
import ru.yofik.athena.common.data.api.ws.model.CommandType
import ru.yofik.athena.common.data.api.ws.model.WsMessage
import ru.yofik.athena.common.data.api.ws.model.WsMessageWithArgument
import ru.yofik.athena.common.data.api.ws.model.messages.JsonDeleteMessage
import ru.yofik.athena.common.data.api.ws.model.messages.JsonNewMessage
import ru.yofik.athena.common.data.api.ws.model.messages.JsonUpdateMessage
import ru.yofik.athena.common.data.api.ws.model.messages.UpdateMessageWsMessage
import ru.yofik.athena.common.domain.model.notification.DeleteMessageNotification
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import ru.yofik.athena.common.domain.model.notification.Notification
import ru.yofik.athena.common.domain.model.notification.UpdateMessageNotification

class MessageNotificationMapper
@Inject
constructor(private val messageApiMapper: MessageApiMapper) : ApiMapper<String?, Notification> {
    private val moshi = Moshi.Builder().build()

    private val messageAdapter = moshi.adapter(WsMessage::class.java)
    private val messageWithArgumentAdapter = moshi.adapter(WsMessageWithArgument::class.java)

    private val newMessageAdapter = moshi.adapter(JsonNewMessage::class.java)
    private val updateMessageAdapter = moshi.adapter(JsonUpdateMessage::class.java)
    private val deleteMessageAdapter = moshi.adapter(JsonDeleteMessage::class.java)

    override fun mapToDomain(entityDTO: String?): Notification {
        if (entityDTO.isNullOrEmpty()) throw MappingException("Invalid data from server!")

        val command: WsMessage =
            messageAdapter.fromJson(entityDTO) ?: throw MappingException("Parse error")

        return when (command.type) {
            CommandType.RECEIVE_NOTIFICATION -> parseReceiveNotificationCommand(entityDTO)
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

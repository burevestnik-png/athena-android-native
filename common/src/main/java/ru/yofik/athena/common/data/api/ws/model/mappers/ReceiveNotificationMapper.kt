package ru.yofik.athena.common.data.api.ws.model.mappers

import com.squareup.moshi.Moshi
import javax.inject.Inject
import ru.yofik.athena.common.data.api.http.model.chat.mappers.MessageApiMapper
import ru.yofik.athena.common.data.api.mapping.ApiMapper
import ru.yofik.athena.common.data.api.mapping.MappingException
import ru.yofik.athena.common.data.api.ws.model.notifications.ReceiveNotification
import ru.yofik.athena.common.domain.model.notification.MessageNotification

class ReceiveNotificationMapper
@Inject
constructor(private val messageApiMapper: MessageApiMapper) :
    ApiMapper<String?, MessageNotification> {
    private val receiveNotificationAdapter =
        Moshi.Builder().build().adapter(ReceiveNotification::class.java)

    override fun mapToDomain(entityDTO: String?): MessageNotification {
        if (entityDTO == null) throw MappingException("Got nullable notification from server")
        val notification =
            receiveNotificationAdapter.fromJson(entityDTO)
                ?: throw MappingException("Error parsing notification json")

        return MessageNotification(message = messageApiMapper.mapToDomain(notification.argument))
    }
}

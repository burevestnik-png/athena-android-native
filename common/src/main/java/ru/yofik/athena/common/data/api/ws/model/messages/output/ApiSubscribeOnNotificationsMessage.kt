package ru.yofik.athena.common.data.api.ws.model.messages

import com.squareup.moshi.Moshi
import ru.yofik.athena.common.data.api.ws.model.CommandType
import ru.yofik.athena.common.data.api.ws.model.WsMessage

class ApiSubscribeOnNotificationsMessage {
    companion object {
        private val adapter = Moshi.Builder().build().adapter(WsMessage::class.java)

        fun toJson(): String {
            return adapter.toJson(WsMessage(CommandType.SUBSCRIBE_ON_NOTIFICATIONS))
        }
    }
}

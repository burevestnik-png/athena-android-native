package ru.yofik.athena.common.data.api.ws.model.notifications

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.ws.model.Command

@JsonClass(generateAdapter = true)
data class SubscribeNotification(@field:Json(name = "command") val command: Command) {
    companion object {
        fun build(): SubscribeNotification {
            return SubscribeNotification(Command.SUBSCRIBE_ON_NOTIFICATIONS)
        }
    }
}

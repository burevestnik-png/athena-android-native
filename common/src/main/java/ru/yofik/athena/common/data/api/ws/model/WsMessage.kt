package ru.yofik.athena.common.data.api.ws.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

enum class CommandType {
    SUBSCRIBE_ON_NOTIFICATIONS,
    RECEIVE_NOTIFICATION
}

@JsonClass(generateAdapter = true)
data class WsMessage(@field:Json(name = "command") val type: CommandType)

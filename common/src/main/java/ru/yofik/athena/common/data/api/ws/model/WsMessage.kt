package ru.yofik.athena.common.data.api.ws.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

internal enum class CommandType {
    SUBSCRIBE_ON_NOTIFICATIONS,
    RECEIVE_NOTIFICATION
}

@JsonClass(generateAdapter = true)
internal data class WsMessage(@field:Json(name = "command") val type: CommandType)

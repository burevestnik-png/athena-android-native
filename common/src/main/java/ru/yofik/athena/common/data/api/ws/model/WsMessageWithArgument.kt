package ru.yofik.athena.common.data.api.ws.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

internal enum class ArgumentType {
    NEW_MESSAGE,
    UPDATED_MESSAGE,
    DELETED_MESSAGES
}

@JsonClass(generateAdapter = true)
internal data class Argument(@field:Json(name = "type") val type: ArgumentType)

@JsonClass(generateAdapter = true)
internal data class WsMessageWithArgument(
    @field:Json(name = "command") val commandType: CommandType,
    @field:Json(name = "argument") val argument: Argument
)

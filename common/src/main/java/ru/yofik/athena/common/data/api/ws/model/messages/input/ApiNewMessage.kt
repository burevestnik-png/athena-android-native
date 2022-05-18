package ru.yofik.athena.common.data.api.ws.model.messages.input

import com.squareup.moshi.*
import ru.yofik.athena.common.data.api.common.apiEntity.ApiMessage
import ru.yofik.athena.common.data.api.ws.model.ArgumentType
import ru.yofik.athena.common.data.api.ws.model.CommandType

@JsonClass(generateAdapter = true)
data class ApiNewMessage(
    @field:Json(name = "type") val type: ArgumentType,
    @field:Json(name = "targetChatId") val chatId: Long,
    @field:Json(name = "payload") val message: ApiMessage
)

@JsonClass(generateAdapter = true)
data class RawNewMessage(
    @field:Json(name = "command") val command: CommandType,
    @field:Json(name = "argument") val argument: ApiNewMessage
)

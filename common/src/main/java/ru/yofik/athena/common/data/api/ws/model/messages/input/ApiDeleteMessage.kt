package ru.yofik.athena.common.data.api.ws.model.messages.input

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.ws.model.ArgumentType
import ru.yofik.athena.common.data.api.ws.model.CommandType

@JsonClass(generateAdapter = true)
internal data class ApiDeleteMessage(
    @field:Json(name = "type") val type: ArgumentType,
    @field:Json(name = "targetChatId") val chatId: Long,
    @field:Json(name = "payload") val messages: List<Long>
)

@JsonClass(generateAdapter = true)
internal data class RawDeleteMessage(
    @field:Json(name = "command") val command: CommandType,
    @field:Json(name = "argument") val argument: ApiDeleteMessage
)

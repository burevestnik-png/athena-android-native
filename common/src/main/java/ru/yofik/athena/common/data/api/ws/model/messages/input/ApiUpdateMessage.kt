package ru.yofik.athena.common.data.api.ws.model.messages.input

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.common.apiEntity.ApiMessage
import ru.yofik.athena.common.data.api.ws.model.ArgumentType
import ru.yofik.athena.common.data.api.ws.model.CommandType

@JsonClass(generateAdapter = true)
data class ApiUpdateMessage(
    @field:Json(name = "type") val type: ArgumentType,
    @field:Json(name = "targetChatId") val chatId: Long,
    @field:Json(name = "payload") val message: ApiMessage
)

@JsonClass(generateAdapter = true)
data class RawUpdateMessage(
    @field:Json(name = "command") val command: CommandType,
    @field:Json(name = "argument") val argument: ApiUpdateMessage
)

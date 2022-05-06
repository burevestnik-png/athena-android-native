package ru.yofik.athena.common.data.api.ws.model.messages

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.ws.model.ArgumentType
import ru.yofik.athena.common.data.api.ws.model.CommandType

@JsonClass(generateAdapter = true)
data class DeleteMessageWsMessage(
    @field:Json(name = "type") val type: ArgumentType,
    @field:Json(name = "deletedMessages") val messages: List<Long>
)

@JsonClass(generateAdapter = true)
data class JsonDeleteMessage(
    @field:Json(name = "command") val command: CommandType,
    @field:Json(name = "argument") val argument: DeleteMessageWsMessage
)

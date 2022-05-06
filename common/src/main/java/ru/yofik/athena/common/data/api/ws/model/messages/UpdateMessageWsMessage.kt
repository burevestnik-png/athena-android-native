package ru.yofik.athena.common.data.api.ws.model.messages

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.chat.responses.dto.MessageDto
import ru.yofik.athena.common.data.api.ws.model.ArgumentType
import ru.yofik.athena.common.data.api.ws.model.CommandType

@JsonClass(generateAdapter = true)
data class UpdateMessageWsMessage(
    @field:Json(name = "type") val type: ArgumentType,
    @field:Json(name = "message") val message: MessageDto
)

@JsonClass(generateAdapter = true)
data class JsonUpdateMessage(
    @field:Json(name = "command") val command: CommandType,
    @field:Json(name = "argument") val argument: UpdateMessageWsMessage
)
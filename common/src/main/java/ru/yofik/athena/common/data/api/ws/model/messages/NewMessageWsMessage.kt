package ru.yofik.athena.common.data.api.ws.model.messages

import com.squareup.moshi.*
import ru.yofik.athena.common.data.api.http.model.chat.responses.dto.MessageDto
import ru.yofik.athena.common.data.api.ws.model.ArgumentType
import ru.yofik.athena.common.data.api.ws.model.CommandType

@JsonClass(generateAdapter = true)
data class NewMessageWsMessage(
    @field:Json(name = "type") val type: ArgumentType,
    @field:Json(name = "payload") val message: MessageDto
)

@JsonClass(generateAdapter = true)
data class JsonNewMessage(
    @field:Json(name = "command") val command: CommandType,
    @field:Json(name = "argument") val argument: NewMessageWsMessage
)

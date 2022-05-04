package ru.yofik.athena.common.data.api.ws.model.messages

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.ws.model.CommandType

@JsonClass(generateAdapter = true)
data class NewMessageWsMessage(
    @field:Json(name = "command") val commandType: CommandType
)

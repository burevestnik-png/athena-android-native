package ru.yofik.athena.common.data.api.ws.model.notifications

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.chat.responses.dto.MessageDto
import ru.yofik.athena.common.data.api.ws.model.Command

@JsonClass(generateAdapter = true)
data class ReceiveNotification(
    @field:Json(name = "command") val command: Command,
    @field:Json(name = "argument") val argument: MessageDto
)

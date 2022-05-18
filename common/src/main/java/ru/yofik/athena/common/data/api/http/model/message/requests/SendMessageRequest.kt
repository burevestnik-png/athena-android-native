package ru.yofik.athena.common.data.api.http.model.message.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendMessageRequest(
    @field:Json(name = "chatId") val chatId: Long,
    @field:Json(name = "text") val text: String
)

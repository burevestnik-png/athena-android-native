package ru.yofik.athena.common.data.api.http.model.chat.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateChatRequest(
    @field:Json(name = "targetUserId") val targetUserId: Long
)

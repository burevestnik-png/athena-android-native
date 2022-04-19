package ru.yofik.athena.common.data.api.model.chat.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateChatRequest(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "users") val users: List<Long>
)

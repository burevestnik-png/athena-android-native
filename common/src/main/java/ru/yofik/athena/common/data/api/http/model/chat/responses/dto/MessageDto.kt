package ru.yofik.athena.common.data.api.http.model.chat.responses.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageDto(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "text") val text: String?,
    @field:Json(name = "senderId") val senderId: Long?,
    @field:Json(name = "chatId") val chatId: Long?,
    @field:Json(name = "creationDate") val creationDate: String?,
    @field:Json(name = "modificationDate") val modificationDate: String?
)

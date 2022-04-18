package ru.yofik.athena.common.data.api.model.chat.responses.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.model.user.responses.dto.UserDto

@JsonClass(generateAdapter = true)
data class ChatDto(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "userViews") val userViews: List<UserDto>?,
    @field:Json(name = "lastMessage") val lastMessage: MessageDto?
)

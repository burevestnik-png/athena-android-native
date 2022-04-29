package ru.yofik.athena.common.data.api.http.model.chat.responses.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.user.responses.dto.UserDto

@JsonClass(generateAdapter = true)
data class ChatWithDetailsDto(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "userViews") val users: List<UserDto>?,
    @field:Json(name = "lastMessage") val message: List<MessageDto>?
)

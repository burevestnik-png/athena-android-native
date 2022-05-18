package ru.yofik.athena.common.data.api.http.model.chat.apiEntity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.user.apiEntity.ApiUser
import ru.yofik.athena.common.data.api.common.apiEntity.ApiMessage

@JsonClass(generateAdapter = true)
data class ApiChatWithDetails(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "users") val users: List<ApiUser>?,
    @field:Json(name = "messages") val messages: List<ApiMessage>?
)

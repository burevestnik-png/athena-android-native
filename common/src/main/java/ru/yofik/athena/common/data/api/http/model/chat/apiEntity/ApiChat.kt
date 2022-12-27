package ru.yofik.athena.common.data.api.http.model.chat.apiEntity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.common.apiEntity.ApiMessage
import ru.yofik.athena.common.data.api.http.model.user.responses.ApiUser
import ru.yofik.athena.common.domain.model.chat.ChatType

@JsonClass(generateAdapter = true)
data class ApiChat(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "type") val type: ChatType?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "userViews") val userViews: List<ApiUser>?,
    @field:Json(name = "lastMessage") val lastMessage: ApiMessage?
)

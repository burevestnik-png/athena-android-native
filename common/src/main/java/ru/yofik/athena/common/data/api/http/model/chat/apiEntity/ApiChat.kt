package ru.yofik.athena.common.data.api.http.model.chat.apiEntity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.common.apiEntity.ApiMessage
import ru.yofik.athena.common.data.api.http.model.apiEntity.ApiUser

@JsonClass(generateAdapter = true)
data class ApiChat(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "userViews") val userViews: List<ApiUser>?,
    @field:Json(name = "lastMessage") val lastMessage: ApiMessage?
)

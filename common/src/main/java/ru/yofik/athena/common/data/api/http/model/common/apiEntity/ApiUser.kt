package ru.yofik.athena.common.data.api.http.model.common.apiEntity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiUser(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "login") val login: String?,
//    @field:Json(name = "isOnline") val isOnline: Boolean?,
//    @field:Json(name = "lastOnlineTime") val lastOnlineTime: String?
)

package ru.yofik.athena.common.data.api.http.model.common.apiEntity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Deprecated(message = "Old auth")
@JsonClass(generateAdapter = true)
data class ApiUserV1(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "login") val login: String?,
//    @field:Json(name = "isOnline") val isOnline: Boolean?,
//    @field:Json(name = "lastOnlineTime") val lastOnlineTime: String?
)

@Deprecated(message = "Old user")
@JsonClass(generateAdapter = true)
data class ApiUserV2(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "email") val email: String?,
    @field:Json(name = "login") val login: String?,
//    @field:Json(name = "isOnline") val isOnline: Boolean?,
//    @field:Json(name = "lastOnlineTime") val lastOnlineTime: String?
)

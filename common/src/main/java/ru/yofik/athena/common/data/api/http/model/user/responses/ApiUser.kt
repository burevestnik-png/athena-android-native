package ru.yofik.athena.common.data.api.http.model.user.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.domain.model.users.Role
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class ApiUser(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "email") val email: String?,
    @field:Json(name = "login") val login: String?,
    @field:Json(name = "role") val role: Role?,
    @field:Json(name = "isLocked") val isLocked: Boolean?,
    @field:Json(name = "lockReason") val lockReason: String?,
    @field:Json(name = "credentialsExpirationTime") val credentialsExpirationTime: LocalDateTime?,
    @field:Json(name = "lastLoginDate") val lastLoginDate: LocalDateTime?,
)
package ru.yofik.athena.common.data.api.model.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDTO(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "login") val login: String?
)

@JsonClass(generateAdapter = true)
class AuthUserResponse(
    @field:Json(name = "payload") val payload: UserDTO,
    code: String,
    status: ResponseStatus
) : Response(code, status)

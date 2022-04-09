package ru.yofik.athena.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true) data class UserDTO(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "login") val login: String?
)

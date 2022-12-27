package ru.yofik.athena.common.data.api.http.model.user.apiEntity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiTokens(
    @field:Json(name = "accessToken") val accessToken: String?,
    @field:Json(name = "refreshToken") val refreshToken: String?,
    @field:Json(name = "expiresIn") val expiresIn: Long?
)

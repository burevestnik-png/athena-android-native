package ru.yofik.athena.common.data.api.http.model.user.responses.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessTokenDto(@field:Json(name = "accessToken") val accessToken: String?)

package ru.yofik.athena.common.data.api.http.model.currentUser.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthUserRequest(@field:Json(name = "accessToken") val accessToken: String)

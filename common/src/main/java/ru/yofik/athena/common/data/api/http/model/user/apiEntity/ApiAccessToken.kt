package ru.yofik.athena.common.data.api.http.model.user.apiEntity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiAccessToken(@field:Json(name = "accessToken") val accessToken: String?)

package ru.yofik.athena.common.data.api.http.model.common.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Response<T>(
    @field:Json(name = "payload") val payload: T,
    @field:Json(name = "httpStatusCode") val code: String,
    @field:Json(name = "status") val status: ResponseStatus,
)

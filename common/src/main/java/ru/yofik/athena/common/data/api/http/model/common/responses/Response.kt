package ru.yofik.athena.common.data.api.http.model.common.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class Response(
    @field:Json(name = "httpStatusCode") val code: String,
    @field:Json(name = "status") val status: ResponseStatus,
)

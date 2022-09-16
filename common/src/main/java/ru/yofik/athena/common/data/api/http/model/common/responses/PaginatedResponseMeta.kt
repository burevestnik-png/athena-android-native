package ru.yofik.athena.common.data.api.http.model.common.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PaginatedResponseMeta(
    @field:Json(name = "sequentialNumber") val sequentialNumber: Int,
    @field:Json(name = "size") val size: Int
)

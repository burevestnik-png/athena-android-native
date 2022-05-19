package ru.yofik.athena.common.data.api.http.model.common.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestWithPagination(
    @field:Json(name = "sequentialNumber") val pageNumber: Int,
    @field:Json(name = "size") val pageSize: Int
)
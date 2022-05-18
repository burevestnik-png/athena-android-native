package ru.yofik.athena.common.data.api.http.model.chat.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetPaginatedChatsRequest(
    @field:Json(name = "sequentialNumber") val pageNumber: Int,
    @field:Json(name = "size") val pageSize: Int
)

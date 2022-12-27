package ru.yofik.athena.common.data.api.http.model.message.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.common.apiEntity.ApiMessage
import ru.yofik.athena.common.data.api.http.model.common.responses.PaginatedResponseMeta

@JsonClass(generateAdapter = true)
class GetPaginatedMessagesResponsePayload(
    @field:Json(name = "meta") val meta: PaginatedResponseMeta,
    @field:Json(name = "content") val messages: List<ApiMessage>
)

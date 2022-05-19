package ru.yofik.athena.common.data.api.http.model.message.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.common.apiEntity.ApiMessage
import ru.yofik.athena.common.data.api.http.model.common.responses.Response
import ru.yofik.athena.common.data.api.http.model.common.responses.ResponseStatus

@JsonClass(generateAdapter = true)
class GetPaginatedMessagesResponse(
    @field:Json(name = "payload") val payload: List<ApiMessage>,
    code: String,
    status: ResponseStatus
) : Response(code, status)

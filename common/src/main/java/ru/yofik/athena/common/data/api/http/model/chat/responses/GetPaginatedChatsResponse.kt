package ru.yofik.athena.common.data.api.http.model.chat.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.chat.apiEntity.ApiChat
import ru.yofik.athena.common.data.api.http.model.common.responses.Response
import ru.yofik.athena.common.data.api.http.model.common.responses.ResponseStatus

@JsonClass(generateAdapter = true)
class GetPaginatedChatsResponse(
    @field:Json(name = "payload") val payload: List<ApiChat>,
    code: String,
    status: ResponseStatus
) : Response(code, status)

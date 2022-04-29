package ru.yofik.athena.common.data.api.http.model.chat.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.Response
import ru.yofik.athena.common.data.api.http.model.ResponseStatus
import ru.yofik.athena.common.data.api.http.model.chat.responses.dto.ChatDto

@JsonClass(generateAdapter = true)
class CreateChatResponse(
    @field:Json(name = "payload") val payload: ChatDto,
    code: String,
    status: ResponseStatus
) :
    Response(
        code,
        status,
    )

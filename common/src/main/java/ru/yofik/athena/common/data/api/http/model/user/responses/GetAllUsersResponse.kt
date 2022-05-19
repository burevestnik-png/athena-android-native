package ru.yofik.athena.common.data.api.http.model.user.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.common.responses.Response
import ru.yofik.athena.common.data.api.http.model.common.responses.ResponseStatus
import ru.yofik.athena.common.data.api.http.model.common.apiEntity.ApiUser

@JsonClass(generateAdapter = true)
class GetAllUsersResponse(
    @field:Json(name = "payload") val payload: List<ApiUser>,
    code: String,
    status: ResponseStatus
) : Response(code, status)

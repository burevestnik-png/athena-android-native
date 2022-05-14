package ru.yofik.athena.common.data.api.http.model.user.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.Response
import ru.yofik.athena.common.data.api.http.model.ResponseStatus
import ru.yofik.athena.common.data.api.http.model.user.dto.ApiUser

@JsonClass(generateAdapter = true)
class GetAllUsersResponse(
    @field:Json(name = "payload") val payload: List<ApiUser>,
    code: String,
    status: ResponseStatus
) : Response(code, status)

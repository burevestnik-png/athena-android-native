package ru.yofik.athena.common.data.api.http.model.user.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.common.responses.Response
import ru.yofik.athena.common.data.api.http.model.common.responses.ResponseStatus
import ru.yofik.athena.common.data.api.http.model.common.apiEntity.ApiUser

@JsonClass(generateAdapter = true)
class GetDefiniteUserResponse(
    code: String,
    status: ResponseStatus,
    @field:Json(name = "payload") val user: ApiUser
) : Response(code, status)

package ru.yofik.athena.common.data.api.http.model.currentUser.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.common.responses.Response
import ru.yofik.athena.common.data.api.http.model.common.responses.ResponseStatus
import ru.yofik.athena.common.data.api.http.model.common.apiEntity.ApiUser

@JsonClass(generateAdapter = true)
class AuthUserResponse(
    @field:Json(name = "payload") val payload: ApiUser,
    code: String,
    status: ResponseStatus
) : Response(code, status)
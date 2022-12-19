package ru.yofik.athena.common.data.api.http.model.user.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.common.apiEntity.ApiUser
import ru.yofik.athena.common.data.api.http.model.common.apiEntity.ApiUserV2
import ru.yofik.athena.common.data.api.http.model.common.responses.Response
import ru.yofik.athena.common.data.api.http.model.common.responses.ResponseStatus

@Deprecated(message = "Old auth")
@JsonClass(generateAdapter = true)
class AuthUserResponse(
    @field:Json(name = "payload") val payload: ApiUser,
    code: String,
    status: ResponseStatus
) : Response(code, status)

@JsonClass(generateAdapter = true)
class AuthUserResponseV2(
    @field:Json(name = "payload") val payload: ApiUserV2,
    code: String,
    status: ResponseStatus
) : Response(code, status)

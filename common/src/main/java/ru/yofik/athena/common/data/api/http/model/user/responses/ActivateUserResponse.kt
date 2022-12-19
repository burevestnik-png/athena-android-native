package ru.yofik.athena.common.data.api.http.model.user.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.common.responses.Response
import ru.yofik.athena.common.data.api.http.model.common.responses.ResponseStatus
import ru.yofik.athena.common.data.api.http.model.user.apiEntity.ApiAccessToken
import ru.yofik.athena.common.data.api.http.model.user.apiEntity.ApiAccessTokenV2

@Deprecated(message = "New auth")
@JsonClass(generateAdapter = true)
class ActivateUserResponse(
    @field:Json(name = "payload") val payload: ApiAccessToken,
    code: String,
    status: ResponseStatus
) : Response(code, status)

@JsonClass(generateAdapter = true)
class ActivateUserResponseV2(
    @field:Json(name = "payload") val payload: ApiAccessTokenV2,
    code: String,
    status: ResponseStatus
) : Response(code, status)


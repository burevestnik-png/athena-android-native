package ru.yofik.athena.common.data.api.model.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessTokenDTO(@field:Json(name = "accessToken") val accessToken: String?)

@JsonClass(generateAdapter = true)
class ActivateUserResponse(
    @field:Json(name = "payload") val payload: AccessTokenDTO,
    code: String,
    status: ResponseStatus
) : Response(code, status)
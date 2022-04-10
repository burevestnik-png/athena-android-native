package ru.yofik.athena.common.data.api.model.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ActivateUserPayload(@field:Json(name = "accessToken") val accessToken: String?)

@JsonClass(generateAdapter = true)
class ActivateUserResponse(
    @field:Json(name = "payload") val payload: ActivateUserPayload,
    code: String,
    status: ResponseStatus
) : Response(code, status)

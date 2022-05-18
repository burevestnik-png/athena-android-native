package ru.yofik.athena.common.data.api.http.model.currentUser.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.Response
import ru.yofik.athena.common.data.api.http.model.ResponseStatus
import ru.yofik.athena.common.data.api.http.model.currentUser.apiEntity.ApiAccessToken

@JsonClass(generateAdapter = true)
class ActivateUserResponse(
    @field:Json(name = "payload") val payload: ApiAccessToken,
    code: String,
    status: ResponseStatus
) : Response(code, status)

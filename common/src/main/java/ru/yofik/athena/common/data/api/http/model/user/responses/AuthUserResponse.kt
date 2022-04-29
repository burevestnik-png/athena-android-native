package ru.yofik.athena.common.data.api.model.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.Response
import ru.yofik.athena.common.data.api.http.model.ResponseStatus
import ru.yofik.athena.common.data.api.http.model.user.responses.dto.UserDto

@JsonClass(generateAdapter = true)
class AuthUserResponse(
    @field:Json(name = "payload") val payload: UserDto,
    code: String,
    status: ResponseStatus
) : Response(code, status)

package ru.yofik.athena.common.data.api.model.user.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.model.Response
import ru.yofik.athena.common.data.api.model.ResponseStatus
import ru.yofik.athena.common.data.api.model.user.responses.dto.UserDto

@JsonClass(generateAdapter = true)
class GetAllUsersResponse(
    @field:Json(name = "payload") val payload: List<UserDto>,
    code: String,
    status: ResponseStatus
) : Response(code, status)

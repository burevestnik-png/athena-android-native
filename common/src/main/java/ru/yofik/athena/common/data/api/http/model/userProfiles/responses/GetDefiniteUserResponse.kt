package ru.yofik.athena.common.data.api.http.model.userProfiles.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.common.apiEntity.ApiUserV1
import ru.yofik.athena.common.data.api.http.model.common.responses.DeprecatedResponse
import ru.yofik.athena.common.data.api.http.model.common.responses.ResponseStatus

@JsonClass(generateAdapter = true)
class GetDefiniteUserResponse(
    code: String,
    status: ResponseStatus,
    @field:Json(name = "payload") val user: ApiUserV1
) : DeprecatedResponse(code, status)

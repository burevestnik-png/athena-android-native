package ru.yofik.athena.common.data.api.http.model.userProfiles.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.common.apiEntity.ApiUserV1
import ru.yofik.athena.common.data.api.http.model.common.responses.DeprecatedResponse
import ru.yofik.athena.common.data.api.http.model.common.responses.ResponseStatus

@JsonClass(generateAdapter = true)
data class GetPaginatedUsersResponsePayload(@field:Json(name = "content") val users: List<ApiUserV1>)

@JsonClass(generateAdapter = true)
class GetPaginatedUsersResponse(
    @field:Json(name = "payload") val payload: GetPaginatedUsersResponsePayload,
    code: String,
    status: ResponseStatus
) : DeprecatedResponse(code, status)

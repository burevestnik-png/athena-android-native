package ru.yofik.athena.common.data.api.http.model.userProfiles.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.common.apiEntity.ApiUser
import ru.yofik.athena.common.data.api.http.model.common.responses.Response
import ru.yofik.athena.common.data.api.http.model.common.responses.ResponseStatus

@JsonClass(generateAdapter = true)
data class GetPaginatedUsersResponsePayload(
    @field:Json(name = "content") val users: List<ApiUser>
)

@JsonClass(generateAdapter = true)
class GetPaginatedUsersResponse(
    @field:Json(name = "payload") val payload: GetPaginatedUsersResponsePayload,
    code: String,
    status: ResponseStatus
) : Response(code, status)

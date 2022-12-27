package ru.yofik.athena.common.data.api.http.model.userProfiles.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.yofik.athena.common.data.api.http.model.user.responses.ApiUser

@JsonClass(generateAdapter = true)
data class GetPaginatedUsersResponsePayload(@field:Json(name = "content") val users: List<ApiUser>)

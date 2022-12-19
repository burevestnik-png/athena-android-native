package ru.yofik.athena.common.data.api.http.model.user.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Deprecated(message = "New Auth")
@JsonClass(generateAdapter = true)
class ActivateUserRequest(@field:Json(name = "invitation") val invitation: String)


@JsonClass(generateAdapter = true)
class ActivateUserRequestV2(
    @field:Json(name = "invitation") val invitation: String,
    @field:Json(name = "userId") val userId: Long
)
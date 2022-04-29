package ru.yofik.athena.common.data.api.http.model.user.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ActivateUserRequest(@field:Json(name = "invitation") val invitation: String)
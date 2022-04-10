package ru.yofik.athena.common.data.api

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import ru.yofik.athena.common.data.api.model.responses.ActivateUserResponse
import ru.yofik.athena.common.data.api.model.requests.ActivateUserRequest

interface UserApi {
    @POST(ApiConstants.ACTIVATE_ENDPOINT)
    suspend fun activate(
        @Body request: ActivateUserRequest,
        // TODO Add interceptor and remove from here header
        @Header("Authorization") authorization: String
    ): ActivateUserResponse
}

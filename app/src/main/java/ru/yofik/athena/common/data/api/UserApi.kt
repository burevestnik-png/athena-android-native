package ru.yofik.athena.common.data.api

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.yofik.athena.common.data.api.ApiParameters.NO_AUTH_HEADER_FULL
import ru.yofik.athena.common.data.api.model.requests.ActivateUserRequest
import ru.yofik.athena.common.data.api.model.responses.ActivateUserResponse

interface UserApi {
    @POST(ApiConstants.ACTIVATE_ENDPOINT)
    @Headers(NO_AUTH_HEADER_FULL)
    suspend fun activate(
        @Body request: ActivateUserRequest,
    ): ActivateUserResponse
}

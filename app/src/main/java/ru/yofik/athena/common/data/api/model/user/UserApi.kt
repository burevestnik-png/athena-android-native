package ru.yofik.athena.common.data.api.model.user

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.yofik.athena.common.data.api.ApiConstants
import ru.yofik.athena.common.data.api.ApiParameters.NO_AUTH_HEADER_FULL
import ru.yofik.athena.common.data.api.model.user.requests.ActivateUserRequest
import ru.yofik.athena.common.data.api.model.user.requests.AuthUserRequest
import ru.yofik.athena.common.data.api.model.responses.ActivateUserResponse
import ru.yofik.athena.common.data.api.model.responses.AuthUserResponse

interface UserApi {
    @POST(ApiConstants.ACTIVATE_ENDPOINT)
    @Headers(NO_AUTH_HEADER_FULL)
    suspend fun activate(
        @Body request: ActivateUserRequest,
    ): ActivateUserResponse

    @POST(ApiConstants.AUTHORIZATION_ENDPOINT)
    @Headers(NO_AUTH_HEADER_FULL)
    suspend fun auth(@Body request: AuthUserRequest): AuthUserResponse
}

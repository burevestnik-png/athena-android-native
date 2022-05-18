package ru.yofik.athena.common.data.api.http.model.currentUser

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.yofik.athena.common.data.api.ApiHttpConstants
import ru.yofik.athena.common.data.api.ApiParameters
import ru.yofik.athena.common.data.api.http.model.currentUser.requests.ActivateUserRequest
import ru.yofik.athena.common.data.api.http.model.currentUser.requests.AuthUserRequest
import ru.yofik.athena.common.data.api.http.model.currentUser.responses.ActivateUserResponse
import ru.yofik.athena.common.data.api.http.model.currentUser.responses.AuthUserResponse

interface CurrentUserApi {
    @POST(ApiHttpConstants.ACTIVATE_ENDPOINT)
    @Headers(ApiParameters.NO_AUTH_HEADER_FULL)
    suspend fun activate(
        @Body request: ActivateUserRequest,
    ): ActivateUserResponse

    @POST(ApiHttpConstants.AUTHORIZATION_ENDPOINT)
    @Headers(ApiParameters.NO_AUTH_HEADER_FULL)
    suspend fun auth(@Body request: AuthUserRequest): AuthUserResponse
}

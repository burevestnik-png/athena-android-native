package ru.yofik.athena.common.data.api.http.model.user

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.yofik.athena.common.data.api.ApiHttpConstants
import ru.yofik.athena.common.data.api.ApiParameters.NO_AUTH_HEADER_FULL
import ru.yofik.athena.common.data.api.http.model.user.requests.ActivateUserRequest
import ru.yofik.athena.common.data.api.http.model.user.requests.AuthUserRequest
import ru.yofik.athena.common.data.api.model.responses.ActivateUserResponse
import ru.yofik.athena.common.data.api.model.responses.AuthUserResponse
import ru.yofik.athena.common.data.api.http.model.user.responses.GetAllUsersResponse

interface UserApi {
    @POST(ApiHttpConstants.ACTIVATE_ENDPOINT)
    @Headers(NO_AUTH_HEADER_FULL)
    suspend fun activate(
        @Body request: ActivateUserRequest,
    ): ActivateUserResponse

    @POST(ApiHttpConstants.AUTHORIZATION_ENDPOINT)
    @Headers(NO_AUTH_HEADER_FULL)
    suspend fun auth(@Body request: AuthUserRequest): AuthUserResponse

    @GET(ApiHttpConstants.ALL_USERS_ENDPOINT)
    suspend fun getAllUsers(): GetAllUsersResponse
}

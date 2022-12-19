package ru.yofik.athena.common.data.api.http.model.user

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.yofik.athena.common.data.api.ApiHttpConstants
import ru.yofik.athena.common.data.api.ApiParameters
import ru.yofik.athena.common.data.api.http.model.user.requests.ActivateUserRequest
import ru.yofik.athena.common.data.api.http.model.user.requests.ActivateUserRequestV2
import ru.yofik.athena.common.data.api.http.model.user.requests.AuthUserRequest
import ru.yofik.athena.common.data.api.http.model.user.responses.ActivateUserResponse
import ru.yofik.athena.common.data.api.http.model.user.responses.ActivateUserResponseV2
import ru.yofik.athena.common.data.api.http.model.user.responses.AuthUserResponse
import ru.yofik.athena.common.data.api.http.model.user.responses.AuthUserResponseV2

interface UserApi {
    @Deprecated(message = "Old auth")
    @POST(ApiHttpConstants.ACTIVATE_ENDPOINT)
    @Headers(ApiParameters.NO_AUTH_HEADER_FULL)
    suspend fun activate(
        @Body request: ActivateUserRequest,
    ): ActivateUserResponse

    @POST(ApiHttpConstants.ACTIVATE_ENDPOINT)
    @Headers(ApiParameters.NO_AUTH_HEADER_FULL)
    suspend fun activateV2(
        @Body request: ActivateUserRequestV2,
    ): ActivateUserResponseV2

    @Deprecated(message = "Old auth")
    @POST(ApiHttpConstants.AUTHORIZATION_ENDPOINT)
    @Headers(ApiParameters.NO_AUTH_HEADER_FULL)
    suspend fun auth(@Body request: AuthUserRequest): AuthUserResponse

    @GET(ApiHttpConstants.AUTHORIZATION_ENDPOINT)
    suspend fun getUserInfo(): AuthUserResponseV2
}

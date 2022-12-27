package ru.yofik.athena.common.data.api.http.model.user

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.yofik.athena.common.data.api.ApiParameters
import ru.yofik.athena.common.data.api.AuthServiceEndpoints
import ru.yofik.athena.common.data.api.http.model.common.responses.Response
import ru.yofik.athena.common.data.api.http.model.user.apiEntity.ApiTokens
import ru.yofik.athena.common.data.api.http.model.user.requests.RefreshUserAccessRequest
import ru.yofik.athena.common.data.api.http.model.user.requests.SignInUserRequest
import ru.yofik.athena.common.data.api.http.model.user.responses.ApiUser

interface UserApi {
    @POST(AuthServiceEndpoints.SIGN_IN_USER)
    @Headers(ApiParameters.NO_AUTH_HEADER_FULL)
    suspend fun signInUser(@Body request: SignInUserRequest): Response<ApiTokens>

    @POST(AuthServiceEndpoints.SIGN_OUT_USER) suspend fun signOutUser()

    @POST(AuthServiceEndpoints.REFRESH_USER_ACCESS)
    suspend fun refreshUserAccess(@Body request: RefreshUserAccessRequest): Response<ApiTokens>

    @GET(AuthServiceEndpoints.GET_CURRENT_USER) suspend fun getUserInfo(): Response<ApiUser>
}

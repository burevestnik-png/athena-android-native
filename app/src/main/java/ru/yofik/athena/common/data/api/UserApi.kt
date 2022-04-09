package ru.yofik.athena.common.data.api

import retrofit2.http.GET
import ru.yofik.athena.common.data.api.model.UserDTO

interface UserApi {
    @GET(ApiConstants.USER_ENDPOINT)
    suspend fun login(): UserDTO
}
package ru.yofik.athena.common.data.api.http.model.user

import retrofit2.http.GET
import ru.yofik.athena.common.data.api.ApiHttpConstants
import ru.yofik.athena.common.data.api.http.model.user.responses.GetAllUsersResponse

interface UserApi {
    @GET(ApiHttpConstants.ALL_USERS_ENDPOINT) suspend fun getAllUsers(): GetAllUsersResponse
}

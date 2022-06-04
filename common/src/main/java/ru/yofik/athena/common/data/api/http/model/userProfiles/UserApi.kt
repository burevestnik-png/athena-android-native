package ru.yofik.athena.common.data.api.http.model.userProfiles

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.yofik.athena.common.data.api.ApiHttpConstants
import ru.yofik.athena.common.data.api.http.model.userProfiles.responses.GetDefiniteUserResponse
import ru.yofik.athena.common.data.api.http.model.userProfiles.responses.GetPaginatedUsersResponse

interface UserApi {
    @GET(ApiHttpConstants.ALL_USERS_ENDPOINT)
    suspend fun getPaginatedUsers(
        @Query("sequentialNumber") sequentialNumber: Int,
        @Query("size") size: Int
    ): GetPaginatedUsersResponse

    @GET("${ApiHttpConstants.ALL_USERS_ENDPOINT}/{id}")
    suspend fun getDefiniteUser(@Path("id") id: Long): GetDefiniteUserResponse
}

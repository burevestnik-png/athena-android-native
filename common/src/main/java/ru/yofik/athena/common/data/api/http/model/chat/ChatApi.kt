package ru.yofik.athena.common.data.api.http.model.chat

import retrofit2.http.*
import ru.yofik.athena.common.data.api.ApiHttpConstants
import ru.yofik.athena.common.data.api.http.model.chat.apiEntity.ApiChat
import ru.yofik.athena.common.data.api.http.model.chat.requests.CreateChatRequest
import ru.yofik.athena.common.data.api.http.model.common.responses.Response

interface ChatApi {
    @GET(ApiHttpConstants.CHATS_ENDPOINT)
    suspend fun getPaginatedChats(
        @Query("sequentialNumber") sequentialNumber: Int,
        @Query("size") size: Int
    ): Response<List<ApiChat>>

    @POST(ApiHttpConstants.CHATS_ENDPOINT)
    suspend fun createChat(@Body createChatRequest: CreateChatRequest): Response<ApiChat>

    @DELETE("${ApiHttpConstants.CHATS_ENDPOINT}/{id}") suspend fun deleteChat(@Path("id") id: Long)
}

package ru.yofik.athena.common.data.api.http.model.chat

import retrofit2.http.*
import ru.yofik.athena.common.data.api.ApiHttpConstants
import ru.yofik.athena.common.data.api.http.model.chat.requests.CreateChatRequest
import ru.yofik.athena.common.data.api.http.model.chat.responses.CreateChatResponse
import ru.yofik.athena.common.data.api.http.model.chat.responses.GetPaginatedChatsResponse

interface ChatApi {
    @GET(ApiHttpConstants.CHATS_ENDPOINT)
    suspend fun getPaginatedChats(
        @Query("sequentialNumber") sequentialNumber: Int,
        @Query("size") size: Int
    ): GetPaginatedChatsResponse

    @POST(ApiHttpConstants.CHATS_ENDPOINT)
    suspend fun createChat(@Body createChatRequest: CreateChatRequest): CreateChatResponse

    @DELETE("${ApiHttpConstants.CHATS_ENDPOINT}/{id}")
    suspend fun deleteChat(@Path("id") id: Long)
}

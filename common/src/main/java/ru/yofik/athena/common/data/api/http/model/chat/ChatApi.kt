package ru.yofik.athena.common.data.api.http.model.chat

import retrofit2.http.*
import ru.yofik.athena.common.data.api.ApiHttpConstants
import ru.yofik.athena.common.data.api.http.model.chat.requests.CreateChatRequest
import ru.yofik.athena.common.data.api.http.model.chat.requests.GetPaginatedChatsRequest
import ru.yofik.athena.common.data.api.http.model.chat.responses.CreateChatResponse
import ru.yofik.athena.common.data.api.http.model.chat.responses.GetAllChatsResponse

interface ChatApi {
    @GET(ApiHttpConstants.CHATS_ENDPOINT)
    suspend fun getPaginatedChats(
        @Body getPaginatedChatsRequest: GetPaginatedChatsRequest
    ): GetAllChatsResponse

    @POST(ApiHttpConstants.CHATS_ENDPOINT)
    suspend fun createChat(@Body createChatRequest: CreateChatRequest): CreateChatResponse

    @DELETE("${ApiHttpConstants.CHATS_ENDPOINT}/{id}") suspend fun deleteChat(@Path("id") id: Long)
}

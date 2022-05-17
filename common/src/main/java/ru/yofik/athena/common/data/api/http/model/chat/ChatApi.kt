package ru.yofik.athena.common.data.api.http.model.chat

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.yofik.athena.common.data.api.ApiHttpConstants.CHATS_ENDPOINT
import ru.yofik.athena.common.data.api.http.model.chat.requests.CreateChatRequest
import ru.yofik.athena.common.data.api.http.model.chat.requests.SendMessageRequest
import ru.yofik.athena.common.data.api.http.model.chat.responses.CreateChatResponse
import ru.yofik.athena.common.data.api.http.model.chat.responses.GetAllChatsResponse
import ru.yofik.athena.common.data.api.http.model.chat.responses.GetChatResponse

interface ChatApi {
    @GET(CHATS_ENDPOINT) suspend fun getAllChats(): GetAllChatsResponse

    @POST(CHATS_ENDPOINT)
    suspend fun createChat(@Body createChatRequest: CreateChatRequest): CreateChatResponse

    @GET("$CHATS_ENDPOINT/{id}/fullView") suspend fun getChat(@Path("id") id: Long): GetChatResponse

    @POST("$CHATS_ENDPOINT/{id}/messages")
    suspend fun sendMessage(@Path("id") id: Long, @Body sendMessageRequest: SendMessageRequest)
}

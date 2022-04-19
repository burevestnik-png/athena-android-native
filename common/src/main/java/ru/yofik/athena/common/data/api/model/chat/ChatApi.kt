package ru.yofik.athena.common.data.api.model.chat

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.yofik.athena.common.data.api.ApiConstants.CHATS_ENDPOINT
import ru.yofik.athena.common.data.api.model.chat.requests.CreateChatRequest
import ru.yofik.athena.common.data.api.model.chat.responses.CreateChatResponse
import ru.yofik.athena.common.data.api.model.chat.responses.GetAllChatsResponse

interface ChatApi {
    @GET(CHATS_ENDPOINT) suspend fun getAllChats(): GetAllChatsResponse

    @POST(CHATS_ENDPOINT)
    suspend fun createChat(@Body createChatRequest: CreateChatRequest): CreateChatResponse
}

package ru.yofik.athena.common.data.api.model.chat

import retrofit2.http.GET
import ru.yofik.athena.common.data.api.ApiConstants.CHATS_ENDPOINT
import ru.yofik.athena.common.data.api.model.chat.responses.GetAllChatsResponse

interface ChatApi {
    @GET(CHATS_ENDPOINT) suspend fun getAllChats(): GetAllChatsResponse
}

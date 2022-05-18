package ru.yofik.athena.common.data.api.http.model.message

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import ru.yofik.athena.common.data.api.ApiHttpConstants
import ru.yofik.athena.common.data.api.http.model.message.requests.SendMessageRequest

interface MessageApi {
    @POST("${ApiHttpConstants.CHATS_ENDPOINT}/{id}/messages")
    suspend fun sendMessage(@Path("id") id: Long, @Body sendMessageRequest: SendMessageRequest)
}

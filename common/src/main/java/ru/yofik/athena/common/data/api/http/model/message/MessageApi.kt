package ru.yofik.athena.common.data.api.http.model.message

import retrofit2.http.*
import ru.yofik.athena.common.data.api.ApiHttpConstants
import ru.yofik.athena.common.data.api.http.model.common.responses.Response
import ru.yofik.athena.common.data.api.http.model.message.requests.SendMessageRequest
import ru.yofik.athena.common.data.api.http.model.message.responses.GetPaginatedMessagesResponsePayload

interface MessageApi {
    @POST("${ApiHttpConstants.CHATS_ENDPOINT}/{chatId}/messages/")
    suspend fun sendMessage(
        @Path("chatId") chatId: Long,
        @Body sendMessageRequest: SendMessageRequest
    )

    @GET("${ApiHttpConstants.CHATS_ENDPOINT}/{chatId}/messages")
    suspend fun getPaginatedMessages(
        @Path("chatId") chatId: Long,
        @Query("sequentialNumber") sequentialNumber: Int,
        @Query("size") size: Int
    ): Response<GetPaginatedMessagesResponsePayload>

    @DELETE("${ApiHttpConstants.CHATS_ENDPOINT}/{chatId}/messages/{messageId}")
    suspend fun deleteDefiniteMessage(
        @Path("chatId") chatId: Long,
        @Path("messageId") messageId: Long,
        @Query("global") isGlobal: Boolean
    )
}

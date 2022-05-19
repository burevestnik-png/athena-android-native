package ru.yofik.athena.common.data.api.http.model.message

import retrofit2.http.*
import ru.yofik.athena.common.data.api.ApiHttpConstants
import ru.yofik.athena.common.data.api.common.apiEntity.ApiMessage
import ru.yofik.athena.common.data.api.http.model.common.requests.RequestWithPagination
import ru.yofik.athena.common.data.api.http.model.message.requests.SendMessageRequest
import ru.yofik.athena.common.data.api.http.model.message.responses.GetPaginatedMessagesResponse

interface MessageApi {
    @POST("${ApiHttpConstants.CHATS_ENDPOINT}/{chatId}/messages")
    suspend fun sendMessage(
        @Path("chatId") chatId: Long,
        @Body sendMessageRequest: SendMessageRequest
    )

    @GET("${ApiHttpConstants.CHATS_ENDPOINT}/{chatId}/messages")
    suspend fun getPaginatedMessages(
        @Path("chatId") chatId: Long,
        @Body requestWithPagination: RequestWithPagination
    ): GetPaginatedMessagesResponse

    @DELETE("${ApiHttpConstants.CHATS_ENDPOINT}/{chatId}/messages/{messageId}")
    suspend fun deleteDefiniteMessage(
        @Path("chatId") chatId: Long,
        @Path("messageId") messageId: Long,
        @Query("global") isGlobal: Boolean
    )
}

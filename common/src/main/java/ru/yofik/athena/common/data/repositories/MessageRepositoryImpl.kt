package ru.yofik.athena.common.data.repositories

import retrofit2.HttpException
import ru.yofik.athena.common.data.api.http.model.chat.ChatApi
import ru.yofik.athena.common.data.api.http.model.chat.requests.SendMessageRequest
import ru.yofik.athena.common.domain.model.exceptions.NetworkException
import ru.yofik.athena.common.domain.repositories.MessageRepository
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val chatApi: ChatApi
) : MessageRepository {
    override suspend fun requestSendMessage(chatId: Long, text: String) {
        try {
            val request = SendMessageRequest(chatId, text)
            chatApi.sendMessage(chatId, request)
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }
}
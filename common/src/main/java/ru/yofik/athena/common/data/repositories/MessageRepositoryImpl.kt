package ru.yofik.athena.common.data.repositories

import javax.inject.Inject
import retrofit2.HttpException
import ru.yofik.athena.common.data.api.common.mappers.ApiMessageMapper
import ru.yofik.athena.common.data.api.http.model.common.requests.RequestWithPagination
import ru.yofik.athena.common.data.api.http.model.message.MessageApi
import ru.yofik.athena.common.data.api.http.model.message.requests.SendMessageRequest
import ru.yofik.athena.common.data.cache.dao.MessageDao
import ru.yofik.athena.common.data.cache.model.CachedMessage
import ru.yofik.athena.common.domain.model.exceptions.NetworkException
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.domain.model.pagination.PaginatedMessages
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.repositories.MessageRepository

class MessageRepositoryImpl
@Inject
constructor(
    private val messageApi: MessageApi,
    private val apiMessageMapper: ApiMessageMapper,
    private val messageDao: MessageDao
) : MessageRepository {

    ///////////////////////////////////////////////////////////////////////////
    // NETWORK
    ///////////////////////////////////////////////////////////////////////////

    override suspend fun requestSendMessage(chatId: Long, text: String) {
        try {
            val request = SendMessageRequest(chatId, text)
            messageApi.sendMessage(chatId, request)
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun requestGetPaginatedMessages(
        chatId: Long,
        pageNumber: Int,
        pageSize: Int
    ): PaginatedMessages {
        val request = RequestWithPagination(pageNumber, pageSize)

        try {
            val response = messageApi.getPaginatedMessages(chatId, request)

            return PaginatedMessages(
                messages = response.payload.map(apiMessageMapper::mapToDomain),
                pagination =
                    Pagination(
                        currentPage = pageNumber + 1,
                        currentAmountOfItems = response.payload.size
                    )
            )
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun requestDeleteDefiniteMessage(
        chatId: Long,
        messageId: Long,
        isGlobal: Boolean
    ) {
        try {
            messageApi.deleteDefiniteMessage(chatId, messageId, isGlobal)
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // CACHE
    ///////////////////////////////////////////////////////////////////////////

    override suspend fun cacheMessage(message: Message) {
        if (message.isNullable) return
        messageDao.insertMessage(CachedMessage.fromDomain(message)!!)
    }
}

package ru.yofik.athena.common.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import ru.yofik.athena.common.data.api.common.mappers.ApiMessageMapper
import ru.yofik.athena.common.data.api.http.model.message.MessageApi
import ru.yofik.athena.common.data.api.http.model.message.requests.SendMessageRequest
import ru.yofik.athena.common.data.cache.Cache
import ru.yofik.athena.common.data.cache.model.CachedMessage
import ru.yofik.athena.common.data.cache.model.toDomain
import ru.yofik.athena.common.domain.model.exceptions.NetworkException
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.domain.model.pagination.PaginatedMessages
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.repositories.MessageRepository
import timber.log.Timber
import javax.inject.Inject

internal class MessageRepositoryImpl
@Inject
constructor(
    private val messageApi: MessageApi,
    private val apiMessageMapper: ApiMessageMapper,
    private val cache: Cache,
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
        pageSize: Int,
    ): PaginatedMessages {
        try {
            val response = messageApi.getPaginatedMessages(chatId, pageNumber, pageSize)
            Timber.d("requestGetPaginatedMessages: $response")

            return PaginatedMessages(
                messages = response.payload.messages.map(apiMessageMapper::mapToDomain),
                pagination =
                Pagination(
                    currentPage = pageNumber + 1,
                    currentAmountOfItems = response.payload.meta.size,
                    pageSize = pageSize
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
        isGlobal: Boolean,
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

    override fun getCachedMessagesByChatId(chatId: Long): Flow<List<Message>> {
        return cache.getAllMessagesByChatId(chatId).map { cachedMessages ->
            cachedMessages.map { it.toDomain() }
        }
    }

    override suspend fun cacheMessage(message: Message) {
        if (message.isNullable) return
        cache.insertMessage(CachedMessage.fromDomain(message)!!)
    }

    override suspend fun cacheMessages(messages: List<Message>) {
        messages.forEach { cacheMessage(it) }
    }

    override suspend fun removeAllCachedMessagesByChatId(chatId: Long) {
        cache.deleteAllMessagesByChatId(chatId)
    }

    override suspend fun removeAllCache() {
        TODO("Not yet implemented")
    }
}

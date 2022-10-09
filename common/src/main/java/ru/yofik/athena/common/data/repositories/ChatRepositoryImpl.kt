package ru.yofik.athena.common.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import retrofit2.HttpException
import ru.yofik.athena.common.data.api.http.model.chat.ChatApi
import ru.yofik.athena.common.data.api.http.model.chat.mappers.ApiChatMapper
import ru.yofik.athena.common.data.api.http.model.chat.requests.CreateChatRequest
import ru.yofik.athena.common.data.cache.Cache
import ru.yofik.athena.common.data.cache.model.CachedChat
import ru.yofik.athena.common.data.cache.model.CachedChatAggregate
import ru.yofik.athena.common.data.cache.model.CachedMessage
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.model.exceptions.NetworkException
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.domain.model.pagination.PaginatedChats
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.repositories.ChatRepository
import timber.log.Timber
import javax.inject.Inject

class ChatRepositoryImpl
@Inject
constructor(
    private val chatApi: ChatApi,
    private val apiChatMapper: ApiChatMapper,
    private val cache: Cache
) : ChatRepository {

    ///////////////////////////////////////////////////////////////////////////
    // NETWORK
    ///////////////////////////////////////////////////////////////////////////

    override suspend fun requestGetPaginatedChats(pageNumber: Int, pageSize: Int): PaginatedChats {
        try {
            val response = chatApi.getPaginatedChats(pageNumber, pageSize)

            return PaginatedChats(
                chats = response.payload.map(apiChatMapper::mapToDomain),
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

    override suspend fun requestCreateChat(targetUserId: Long): Chat {
        try {
            val request = CreateChatRequest(targetUserId)

            val response = chatApi.createChat(request)
            Timber.d("requestCreateChat: ${response.payload}")

            return apiChatMapper.mapToDomain(response.payload)
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun requestDeleteChat(chatId: Long) {
        try {
            chatApi.deleteChat(chatId)
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // CACHE
    ///////////////////////////////////////////////////////////////////////////

    override fun getCachedChats(): Flow<List<Chat>> {
        return cache
            .getChats()
            .map { cachedChatAggregates ->
                cachedChatAggregates.map { CachedChat.toDomain(it.chat, it.users, it.lastMessage) }
            }
            .filter { chats -> chats.none { it.lastMessage.isNullable && it.users.isEmpty() } }
            .onEach { Timber.d("getCachedChats: $it") }
    }

    override suspend fun getCachedChat(id: Long): Chat {
        val cachedChatAggregate = cache.getChat(id)
        return CachedChat.toDomain(
            cachedChat = cachedChatAggregate.chat,
            users = cachedChatAggregate.users,
            lastMessage = cachedChatAggregate.lastMessage
        )
    }

    override suspend fun cacheChats(chats: List<Chat>) {
        cache.insertChats(chats.map { CachedChatAggregate.fromDomain(it) })
    }

    override suspend fun updateLastMessage(message: Message) {
        cache.updateLastMessageByChatId(CachedMessage.fromDomain(message)!!)
    }

    override suspend fun removeAllCache() {
        cache.deleteAllChats()
    }
}

package ru.yofik.athena.common.data.repositories

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import ru.yofik.athena.common.data.api.http.model.chat.ChatApi
import ru.yofik.athena.common.data.api.http.model.chat.mappers.ApiChatMapper
import ru.yofik.athena.common.data.api.http.model.chat.requests.CreateChatRequest
import ru.yofik.athena.common.data.api.http.model.common.requests.RequestWithPagination
import ru.yofik.athena.common.data.cache.Cache
import ru.yofik.athena.common.data.cache.model.CachedChat
import ru.yofik.athena.common.data.cache.model.CachedChatAggregate
import ru.yofik.athena.common.data.preferences.Preferences
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.model.exceptions.NetworkException
import ru.yofik.athena.common.domain.model.pagination.PaginatedChats
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.repositories.ChatRepository
import timber.log.Timber

class ChatRepositoryImpl
@Inject
constructor(
    private val chatApi: ChatApi,
    private val apiChatMapper: ApiChatMapper,
    private val preferences: Preferences,
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

    override suspend fun requestCreateChat(name: String, userId: Long): Chat {
        try {
            val initiatorId = preferences.getCurrentUserId()
            val request = CreateChatRequest(name = name, users = listOf(initiatorId, userId))

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

    override fun getAllChats(): Flow<List<Chat>> {
        return cache.getChats().map { chats ->
            chats.map { CachedChat.toDomain(it.chat, it.users, it.lastMessage) }
        }
    }

    override suspend fun cacheChats(chats: List<Chat>) {
        cache.insertChats(chats.map { CachedChatAggregate.fromDomain(it) })
    }
}

package ru.yofik.athena.common.data.repositories

import javax.inject.Inject
import retrofit2.HttpException
import ru.yofik.athena.common.data.api.model.chat.ChatApi
import ru.yofik.athena.common.data.api.model.chat.mappers.ChatDtoMapper
import ru.yofik.athena.common.data.api.model.chat.requests.CreateChatRequest
import ru.yofik.athena.common.data.preferences.Preferences
import ru.yofik.athena.common.domain.model.NetworkException
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.repositories.ChatRepository
import timber.log.Timber

class ChatRepositoryImpl
@Inject
constructor(
    private val chatApi: ChatApi,
    private val chatDtoMapper: ChatDtoMapper,
    private val preferences: Preferences
) : ChatRepository {
    override suspend fun requestGetAllChats(): List<Chat> {
        try {
            val response = chatApi.getAllChats()

            return response.payload.map(chatDtoMapper::mapToDomain)
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun requestCreateChat(name: String, userId: Long): Chat {
        try {
            val fromUserId = preferences.getUser().id
            val request = CreateChatRequest(name = name, users = listOf(fromUserId, userId))

            val response = chatApi.createChat(request)
            Timber.d("requestCreateChat: ${response.payload}")

            return chatDtoMapper.mapToDomain(response.payload)
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }
}
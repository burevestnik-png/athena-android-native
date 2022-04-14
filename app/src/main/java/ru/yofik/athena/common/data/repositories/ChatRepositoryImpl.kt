package ru.yofik.athena.common.data.repositories

import retrofit2.HttpException
import ru.yofik.athena.common.data.api.model.chat.ChatApi
import ru.yofik.athena.common.data.api.model.chat.mappers.ChatDtoMapper
import ru.yofik.athena.common.domain.model.NetworkException
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.repositories.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatApi: ChatApi,
    private val chatDtoMapper: ChatDtoMapper
) : ChatRepository {
    override suspend fun requestGetAllChats(): List<Chat> {
        try {
            val response = chatApi.getAllChats()

            return response.payload.map(chatDtoMapper::mapToDomain)
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }
}
package ru.yofik.athena.common.domain.repositories

interface MessageRepository {
    suspend fun requestSendMessage(chatId: Long, text: String)
}
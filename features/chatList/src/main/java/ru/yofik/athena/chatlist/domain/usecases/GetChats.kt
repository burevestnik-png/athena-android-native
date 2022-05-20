package ru.yofik.athena.chatlist.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.repositories.ChatRepository

class GetChats @Inject constructor(private val chatRepository: ChatRepository) {
    operator fun invoke() = chatRepository.getAllChats()
}

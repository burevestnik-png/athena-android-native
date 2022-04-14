package ru.yofik.athena.chatList.domain.usecases

import ru.yofik.athena.common.domain.model.chat.Chat
import javax.inject.Inject
import ru.yofik.athena.common.domain.repositories.UserRepository

class GetAllChats @Inject constructor() {
    operator fun invoke(): List<Chat> {
        return emptyList()
    }
}

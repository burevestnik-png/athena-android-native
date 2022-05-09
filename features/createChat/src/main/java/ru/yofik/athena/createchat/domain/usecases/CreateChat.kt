package ru.yofik.athena.createchat.domain.usecases

import org.threeten.bp.LocalDateTime
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.model.message.Message
import javax.inject.Inject
import ru.yofik.athena.common.domain.repositories.ChatRepository
import timber.log.Timber

class CreateChat @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(name: String, id: Long): Chat {
        val allChats = chatRepository.requestGetAllChats()
        Timber.d("invoke: ${allChats.joinToString("\n")}")

        // todo delete crutch
        for (chat in allChats) {
            Timber.d("invoke: $chat")
            if (chat.users.any { it.id == id }) {
                Timber.d("invoke in if: $chat")
                return Chat(
                    id = chat.id,
                    name = chat.name,
                    users = chat.users,
                    lastMessage = Message(
                        id = Message.NULLABLE_MESSAGE_ID,
                        content = "sd",
                        senderId = 1,
                        chatId = 1,
                        creationDate = LocalDateTime.now(),
                        modificationDate = LocalDateTime.now()
                    )
                )
            }
        }

        return chatRepository.requestCreateChat(name, id)
    }
}
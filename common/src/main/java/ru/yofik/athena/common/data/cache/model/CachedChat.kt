package ru.yofik.athena.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.model.chat.ChatType

@Entity(tableName = "chats")
data class CachedChat(
    @PrimaryKey(autoGenerate = false) val chatId: Long,
    val name: String,
    val type: ChatType
) {
    companion object {
        fun fromDomain(chat: Chat): CachedChat {
            return CachedChat(chatId = chat.id, name = chat.name, type = chat.type)
        }

        fun toDomain(
            cachedChat: CachedChat,
            users: List<CachedUser>,
            lastMessage: CachedMessage
        ): Chat {
            return Chat(
                id = cachedChat.chatId,
                type = cachedChat.type,
                name = cachedChat.name,
                users = users.map(CachedUser::toDomain),
                lastMessage = CachedMessage.toDomain(lastMessage)
            )
        }
    }
}

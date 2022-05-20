package ru.yofik.athena.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.yofik.athena.common.domain.model.chat.Chat

@Entity(tableName = "chats")
data class CachedChat(@PrimaryKey(autoGenerate = false) val chatId: Long, val name: String) {
    companion object {
        fun fromDomain(chat: Chat): CachedChat {
            return CachedChat(chatId = chat.id, name = chat.name)
        }

        fun toDomain(
            cachedChat: CachedChat,
            users: List<CachedUser>,
            lastMessage: CachedMessage
        ): Chat {
            return Chat(
                id = cachedChat.chatId,
                name = cachedChat.name,
                users = users.map(CachedUser::toDomain),
                lastMessage = CachedMessage.toDomain(lastMessage)
            )
        }
    }
}

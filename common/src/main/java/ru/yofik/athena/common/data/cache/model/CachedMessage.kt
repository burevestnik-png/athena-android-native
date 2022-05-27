package ru.yofik.athena.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime
import ru.yofik.athena.common.domain.model.message.Message

@Entity(tableName = "messages")
data class CachedMessage(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val content: String,
    val senderId: Long,
    val chatId: Long,
    val creationDate: LocalDateTime,
    val modificationDate: LocalDateTime
) {
    companion object {
        fun fromDomain(message: Message): CachedMessage? {
            if (message.isNullable) return null
            return CachedMessage(
                id = message.id,
                content = message.content,
                senderId = message.senderId,
                chatId = message.chatId,
                creationDate = message.creationDate,
                modificationDate = message.modificationDate
            )
        }

        fun toDomain(cachedMessage: CachedMessage?): Message {
            if (cachedMessage == null) return Message.nullable()
            return Message(
                id = cachedMessage.id,
                content = cachedMessage.content,
                senderId = cachedMessage.senderId,
                chatId = cachedMessage.chatId,
                creationDate = cachedMessage.creationDate,
                modificationDate = cachedMessage.modificationDate
            )
        }
    }
}

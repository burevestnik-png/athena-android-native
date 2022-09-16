package ru.yofik.athena.common.data.cache.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime
import ru.yofik.athena.common.domain.model.message.Message

@Entity(
    tableName = "messages",
    foreignKeys =
        [
            ForeignKey(
                entity = CachedUser::class,
                parentColumns = ["userId"],
                childColumns = ["senderId"],
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
            ),
            ForeignKey(
                entity = CachedChat::class,
                parentColumns = ["chatId"],
                childColumns = ["chatId"],
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
            )
        ],
    indices = [Index("messageId"), Index("senderId"), Index("chatId")]
)
data class CachedMessage(
    @PrimaryKey(autoGenerate = false) val messageId: Long,
    val content: String,
    val senderId: Long,
    val chatId: Long,
    val creationDate: LocalDateTime,
    val modificationDate: LocalDateTime,
) {
    companion object {
        fun fromDomain(message: Message): CachedMessage? {
            if (message.isNullable) return null
            return CachedMessage(
                messageId = message.id,
                content = message.content,
                senderId = message.senderId,
                chatId = message.chatId,
                creationDate = message.creationDate,
                modificationDate = message.modificationDate
            )
        }
    }
}

fun CachedMessage?.toDomain(): Message {
    if (this == null) return Message.nullable()
    return Message(
        id = this.messageId,
        content = this.content,
        senderId = this.senderId,
        chatId = this.chatId,
        creationDate = this.creationDate,
        modificationDate = this.modificationDate
    )
}

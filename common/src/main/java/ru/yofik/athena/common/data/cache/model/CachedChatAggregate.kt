package ru.yofik.athena.common.data.cache.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.yofik.athena.common.domain.model.chat.Chat

data class CachedChatAggregate(
    @Embedded val chat: CachedChat,
    @Relation(
        parentColumn = "chatId",
        entityColumn = "userId",
        associateBy = Junction(CachedChatUserCrossRef::class)
    )
    val users: List<CachedUser>,
    @Relation(parentColumn = "chatId", entityColumn = "chatId") val lastMessage: CachedMessage?
) {
    companion object {
        fun fromDomain(chat: Chat): CachedChatAggregate {
            return CachedChatAggregate(
                chat = CachedChat.fromDomain(chat),
                users = chat.users.map(CachedUser::fromDomain),
                lastMessage = CachedMessage.fromDomain(chat.lastMessage)
            )
        }
    }
}

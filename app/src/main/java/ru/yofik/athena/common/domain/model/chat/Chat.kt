package ru.yofik.athena.common.domain.model.chat

import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.domain.model.user.User
import java.util.*

data class Chat(
    val id: Int,
    val name: String,
    val lastMessage: Message
) {
    companion object {
        // TODO remove in future
        fun getLeshaChat() =
            Chat(
                id = 1,
                name = User.getLesha().name,
                lastMessage = Message.getLeshaLastMsg(123)
            )
    }
}

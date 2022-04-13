package ru.yofik.athena.common.domain.model.message

import org.threeten.bp.LocalDateTime
import ru.yofik.athena.common.domain.model.user.User

/** Basic entity object */
data class Message(
    val id: Int,
    val text: String,
    val senderId: Long,
    val chatId: Int,
    val date: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        // TODO remove in future
        fun getLeshaLastMsg(id: Int) =
            Message(
                id = 1,
                text = "Ну что, идем на англ?",
                senderId = User.getLesha().id,
                chatId = id
            )
    }
}

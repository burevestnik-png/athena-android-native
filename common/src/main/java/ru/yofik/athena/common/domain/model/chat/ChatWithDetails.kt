package ru.yofik.athena.common.domain.model.chat

import ru.yofik.athena.common.domain.model.chat.details.Details
import ru.yofik.athena.common.domain.model.user.User

/** Basic entity object which has more details than base entity Chat */
data class ChatWithDetails(
    val id: Long,
    val name: String,
    val users: List<User>,
    val details: Details
) {
    companion object {
        // todo is it good
        fun createNullChat() =
            ChatWithDetails(id = -1, name = "", users = emptyList(), details = Details(emptyList()))
    }
}

package ru.yofik.athena.chat.domain.model.mappers

import ru.yofik.athena.chat.domain.model.UiChat
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.presentation.model.UiMapper
import javax.inject.Inject

class UiSenderMapper @Inject constructor() : UiMapper<Pair<Message, UiChat>, String> {
    override fun mapToView(model: Pair<Message, UiChat>): String {
        val (message, chat) = model

        return chat.users.first { it.id == message.senderId }.name
    }
}

package ru.yofik.athena.chat.domain.model.mappers

import ru.yofik.athena.chat.domain.model.UiChat
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.presentation.model.UiMapper
import timber.log.Timber
import javax.inject.Inject

class UiSenderMapper @Inject constructor() : UiMapper<Pair<Message, UiChat>, String> {
    override fun mapToView(model: Pair<Message, UiChat>): String {
        val (message, chat) = model
        Timber.d("mapToView: ${chat.users}")
        return chat.users.first { it.id == message.senderId }.name
    }
}

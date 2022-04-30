package ru.yofik.athena.chat.domain.model.mappers

import ru.yofik.athena.chat.domain.model.UiChat
import ru.yofik.athena.common.domain.model.chat.ChatWithDetails
import ru.yofik.athena.common.presentation.model.UiMapper
import javax.inject.Inject

class UiChatMapper @Inject constructor() : UiMapper<ChatWithDetails, UiChat> {
    override fun mapToView(model: ChatWithDetails): UiChat {
        return UiChat(
            id = model.id,
            users = model.users,
            name = model.name
        )
    }
}
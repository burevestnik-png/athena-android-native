package ru.yofik.athena.common.domain.model.pagination

import ru.yofik.athena.common.domain.model.chat.Chat

data class PaginatedChats(val chats: List<Chat>, val pagination: Pagination)

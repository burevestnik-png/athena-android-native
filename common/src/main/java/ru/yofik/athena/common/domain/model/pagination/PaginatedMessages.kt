package ru.yofik.athena.common.domain.model.pagination

import ru.yofik.athena.common.domain.model.message.Message

data class PaginatedMessages(
    val messages:  List<Message>,
    val pagination: Pagination
)

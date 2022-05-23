package ru.yofik.athena.common.domain.model.pagination

import ru.yofik.athena.common.domain.model.user.User

data class PaginatedUsers(val users: List<User>, val pagination: Pagination)

package ru.yofik.athena.common.domain.model.pagination

import ru.yofik.athena.common.domain.model.users.UserV2

data class PaginatedUsers(val users: List<UserV2>, val pagination: Pagination)

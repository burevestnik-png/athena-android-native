package ru.yofik.athena.createchat.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.model.exceptions.NoMoreItemsException
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.repositories.UserProfileRepository

class RequestNextUsersPage
@Inject
constructor(
    private val userProfileRepository: UserProfileRepository,
) {
    suspend operator fun invoke(
        pageNumber: Int,
        pageSize: Int = Pagination.DEFAULT_PAGE_SIZE
    ): Pagination {
        val (users, pagination) = userProfileRepository.requestGetPaginatedUsers(pageNumber, pageSize)

        userProfileRepository.cacheUsers(users)

        if (!pagination.canLoadMore) {
            throw NoMoreItemsException("No more users available")
        }

        return pagination
    }
}

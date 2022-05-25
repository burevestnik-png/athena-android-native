package ru.yofik.athena.createchat.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.model.exceptions.NoMoreItemsException
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.repositories.CurrentUserRepository
import ru.yofik.athena.common.domain.repositories.UserRepository

class RequestNextUsersPage
@Inject
constructor(
    private val userRepository: UserRepository,
    private val currentUserRepository: CurrentUserRepository
) {
    suspend operator fun invoke(
        pageNumber: Int,
        pageSize: Int = Pagination.DEFAULT_PAGE_SIZE
    ): Pagination {
        val currentUserId = currentUserRepository.getCache().id
        val (users, pagination) = userRepository.requestGetPaginatedUsers(pageNumber, pageSize)

        userRepository.cacheUsers(users)

        if (!pagination.canLoadMore) {
            throw NoMoreItemsException("No more users available")
        }

        return pagination
    }
}

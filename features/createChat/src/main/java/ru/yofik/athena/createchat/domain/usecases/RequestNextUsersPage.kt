package ru.yofik.athena.createchat.domain.usecases

import ru.yofik.athena.common.domain.model.exceptions.NoMoreItemsExceptions
import javax.inject.Inject
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

        if (!pagination.canLoadMore) {
            throw NoMoreItemsExceptions("No more chats available")
        }

        userRepository.cacheUsers(users)

        return pagination
    }
}

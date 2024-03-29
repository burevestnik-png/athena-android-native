package ru.yofik.athena.createchat.domain.usecases

import ru.yofik.athena.common.domain.model.exceptions.NoMoreItemsException
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.repositories.UserProfileRepository
import javax.inject.Inject

class RequestNextUsersPage
@Inject
constructor(
    private val userProfileRepository: UserProfileRepository,
) {
    suspend operator fun invoke(
        pageNumber: Int,
        pageSize: Int = Pagination.DEFAULT_PAGE_SIZE
    ): Pagination {
        val (users, pagination) =
            userProfileRepository.requestGetPaginatedUsersProfiles(pageNumber, pageSize)

        userProfileRepository.cacheUsers(users)

        if (!pagination.canLoadMore) {
            throw NoMoreItemsException("No more users available")
        }

        return pagination
    }
}

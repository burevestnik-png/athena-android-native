package ru.yofik.athena.chat.domain.usecases

import ru.yofik.athena.common.domain.repositories.CurrentUserRepository
import javax.inject.Inject

class GetCurrentUserId @Inject constructor(
    private val currentUserRepository: CurrentUserRepository
) {
    operator fun invoke(): Long {
        return currentUserRepository.getCache().id
    }
}
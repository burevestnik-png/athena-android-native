package ru.yofik.athena.chat.domain.usecases

import ru.yofik.athena.common.domain.repositories.CurrentUserRepository
import ru.yofik.athena.common.domain.repositories.UserRepository
import javax.inject.Inject

class GetUserId @Inject constructor(
    private val currentUserRepository: CurrentUserRepository
) {
    operator fun invoke(): Long {
        return currentUserRepository.getCache().id
    }
}
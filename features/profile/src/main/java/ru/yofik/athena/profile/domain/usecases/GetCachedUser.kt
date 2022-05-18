package ru.yofik.athena.profile.domain.usecases

import ru.yofik.athena.common.domain.model.user.User
import ru.yofik.athena.common.domain.repositories.CurrentUserRepository
import ru.yofik.athena.common.domain.repositories.UserRepository
import javax.inject.Inject

class GetCachedUser @Inject constructor(
    private val currentUserRepository: CurrentUserRepository
) {
    operator fun invoke(): User {
        return currentUserRepository.getCache()
    }
}
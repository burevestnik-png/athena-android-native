package ru.yofik.athena.settings.domain.usecases

import ru.yofik.athena.common.domain.model.user.User
import ru.yofik.athena.common.domain.repositories.UserRepository
import javax.inject.Inject

class GetCachedUser @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): User {
        return userRepository.getCachedUser()
    }
}
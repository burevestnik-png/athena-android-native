package ru.yofik.athena.common.domain.usecases

import ru.yofik.athena.common.domain.model.users.User
import ru.yofik.athena.common.domain.model.users.UserV2
import ru.yofik.athena.common.domain.repositories.UserRepository
import javax.inject.Inject

class GetCachedUser @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(): UserV2 {
        return userRepository.getCachedUser()
    }
}

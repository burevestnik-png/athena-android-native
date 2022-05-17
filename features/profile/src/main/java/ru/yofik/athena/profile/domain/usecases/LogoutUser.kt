package ru.yofik.athena.profile.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.repositories.UserRepository

class LogoutUser @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke() {
        userRepository.apply {
            removeCachedCurrentUser()
            removeUserAccessToken()
        }
    }
}

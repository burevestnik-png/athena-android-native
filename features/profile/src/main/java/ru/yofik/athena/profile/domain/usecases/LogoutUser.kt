package ru.yofik.athena.profile.domain.usecases

import ru.yofik.athena.common.domain.repositories.UserRepository
import javax.inject.Inject

class LogoutUser @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke() {
        userRepository.apply {
            removeAccessToken()
            removeAllCache()
        }
    }
}

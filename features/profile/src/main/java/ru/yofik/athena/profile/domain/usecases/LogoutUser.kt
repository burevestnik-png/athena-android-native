package ru.yofik.athena.profile.domain.usecases

import ru.yofik.athena.common.domain.repositories.CurrentUserRepository
import javax.inject.Inject
import ru.yofik.athena.common.domain.repositories.UserRepository

class LogoutUser @Inject constructor(private val currentUserRepository: CurrentUserRepository) {
    suspend operator fun invoke() {
        currentUserRepository.apply {
            removeAccessToken()
            removeCache()
        }
    }
}

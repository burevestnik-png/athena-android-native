package ru.yofik.athena.profile.domain.usecases

import ru.yofik.athena.common.domain.repositories.CurrentUserRepository
import javax.inject.Inject

class LogoutUser @Inject constructor(private val currentUserRepository: CurrentUserRepository) {
    operator fun invoke() {
        currentUserRepository.apply {
            removeAccessToken()
            removeAllCache()
        }
    }
}

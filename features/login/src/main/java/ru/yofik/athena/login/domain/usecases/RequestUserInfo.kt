package ru.yofik.athena.login.domain.usecases

import ru.yofik.athena.common.domain.repositories.CurrentUserRepository
import javax.inject.Inject
import ru.yofik.athena.common.domain.repositories.UserRepository

class RequestUserInfo @Inject constructor(private val currentUserRepository: CurrentUserRepository) {
    suspend operator fun invoke() {
        currentUserRepository.requestGetInfo()
    }
}

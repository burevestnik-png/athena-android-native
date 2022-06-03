package ru.yofik.athena.login.domain.usecases

import ru.yofik.athena.common.domain.repositories.CurrentUserRepository
import javax.inject.Inject

class RequestUserActivation @Inject constructor(private val currentUserRepository: CurrentUserRepository) {
    suspend operator fun invoke(code: String) {
        currentUserRepository.requestCurrentUserActivation(code)
    }
}

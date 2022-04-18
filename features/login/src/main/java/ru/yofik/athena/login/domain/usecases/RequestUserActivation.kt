package ru.yofik.athena.login.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.repositories.UserRepository

class RequestUserActivation @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(code: String) {
        userRepository.requestActivateUser(code)
    }
}

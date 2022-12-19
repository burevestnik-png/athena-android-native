package ru.yofik.athena.login.domain.usecases

import ru.yofik.athena.common.domain.repositories.UserRepository
import javax.inject.Inject

class RequestUserActivation @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(code: String, userId: Long) {
        val token = userRepository.requestUserActivation(code, userId)
        userRepository.cacheAccessToken(token)
    }
}

package ru.yofik.athena.login.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.repositories.UserRepository
import timber.log.Timber

class SignInUser @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(code: String, userId: Long) {
        val tokens = userRepository.requestSignIn(code, userId)
        userRepository.cacheTokens(tokens)

        val user = userRepository.requestGetCurrentUser()
        userRepository.cacheUser(user)
    }
}

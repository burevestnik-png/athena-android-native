package ru.yofik.athena.login.domain.usecases

import ru.yofik.athena.common.domain.repositories.UserRepository
import javax.inject.Inject

class RequestUserInfo @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke() {
        userRepository.requestGetUserInfo()
    }
}

package ru.yofik.athena.chat.domain.usecases

import ru.yofik.athena.common.domain.repositories.UserRepository
import javax.inject.Inject

class GetUserId @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Long {
        return userRepository.getCachedUser().id
    }
}
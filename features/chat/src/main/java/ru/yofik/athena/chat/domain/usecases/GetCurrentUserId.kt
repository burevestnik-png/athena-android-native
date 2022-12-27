package ru.yofik.athena.chat.domain.usecases

import ru.yofik.athena.common.domain.repositories.UserRepository
import javax.inject.Inject

class GetCurrentUserId @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Long {
        return userRepository.getCachedUser().id
    }
}

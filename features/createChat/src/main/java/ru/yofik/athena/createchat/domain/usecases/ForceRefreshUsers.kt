package ru.yofik.athena.createchat.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.repositories.UserRepository

class ForceRefreshUsers @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke() {
        userRepository.removeCachedUsers()
    }
}

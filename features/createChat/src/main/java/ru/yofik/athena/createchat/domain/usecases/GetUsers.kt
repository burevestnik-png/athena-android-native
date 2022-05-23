package ru.yofik.athena.createchat.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.repositories.UserRepository

class GetUsers @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getCachedUsers()
}

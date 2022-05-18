package ru.yofik.athena.createchat.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.model.user.User
import ru.yofik.athena.common.domain.repositories.CurrentUserRepository
import ru.yofik.athena.common.domain.repositories.UserRepository

class RequestGetAllUsers
@Inject
constructor(
    private val userRepository: UserRepository,
    private val currentUserRepository: CurrentUserRepository
) {
    suspend operator fun invoke(): List<User> {
        val currentUserId = currentUserRepository.getCache().id
        val requestedUsers = userRepository.requestGetAllUsers().filter { it.id != currentUserId }

        userRepository.cacheUsers(requestedUsers)

        return requestedUsers
    }
}

package ru.yofik.athena.createchat.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.model.user.User
import ru.yofik.athena.common.domain.repositories.UserRepository

class RequestGetAllUsers @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): List<User> {
        val currentUserId = userRepository.getCachedCurrentUser().id
        val requestedUsers = userRepository.requestGetAllUsers().filter { it.id != currentUserId }

        userRepository.storeUsers(requestedUsers)

        return requestedUsers
    }
}

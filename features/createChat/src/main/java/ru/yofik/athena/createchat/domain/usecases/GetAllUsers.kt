package ru.yofik.athena.createchat.domain.usecases

import ru.yofik.athena.common.domain.model.user.User
import ru.yofik.athena.common.domain.repositories.UserRepository
import javax.inject.Inject

class GetAllUsers @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): List<User> {
        return userRepository.requestGetAllUsers()
    }
}
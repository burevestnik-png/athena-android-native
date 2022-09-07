package ru.yofik.athena.createchat.domain.usecases

import kotlinx.coroutines.flow.map
import ru.yofik.athena.common.domain.repositories.UserProfileRepository
import ru.yofik.athena.common.domain.repositories.UserRepository
import javax.inject.Inject

class GetUsers @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke() = userProfileRepository.getCachedUsers()
        .map { it.filter { user -> user.id != userRepository.getCachedUser().id } }
}

package ru.yofik.athena.createchat.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.repositories.UserProfileRepository

class GetUsers @Inject constructor(private val userProfileRepository: UserProfileRepository) {
    operator fun invoke() = userProfileRepository.getCachedUsers()
}

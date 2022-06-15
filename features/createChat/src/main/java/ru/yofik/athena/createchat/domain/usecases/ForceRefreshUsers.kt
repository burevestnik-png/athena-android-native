package ru.yofik.athena.createchat.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.repositories.UserProfileRepository

class ForceRefreshUsers @Inject constructor(private val userProfileRepository: UserProfileRepository) {
    suspend operator fun invoke() {
        userProfileRepository.removeAllCache()
    }
}

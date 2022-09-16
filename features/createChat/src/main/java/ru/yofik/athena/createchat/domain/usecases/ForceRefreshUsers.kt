package ru.yofik.athena.createchat.domain.usecases

import ru.yofik.athena.common.domain.repositories.UserProfileRepository
import javax.inject.Inject

class ForceRefreshUsers
@Inject
constructor(private val userProfileRepository: UserProfileRepository) {
    suspend operator fun invoke() {
        userProfileRepository.removeAllCache()
    }
}

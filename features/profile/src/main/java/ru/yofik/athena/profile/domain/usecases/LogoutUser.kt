package ru.yofik.athena.profile.domain.usecases

import ru.yofik.athena.common.domain.repositories.CommonRepository
import ru.yofik.athena.common.domain.repositories.UserRepository
import javax.inject.Inject

class LogoutUser @Inject constructor(private val commonRepository: CommonRepository) {
    suspend operator fun invoke() {
        commonRepository.removeAllCache()
    }
}

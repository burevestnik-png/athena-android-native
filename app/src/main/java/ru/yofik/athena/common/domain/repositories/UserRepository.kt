package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.data.api.model.responses.ActivateUserResponse
import ru.yofik.athena.common.domain.model.user.User

interface UserRepository {
    suspend fun activateUser(code: String): ActivateUserResponse
}
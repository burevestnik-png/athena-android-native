package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.domain.model.user.User

interface UserRepository {
    suspend fun activateUser(code: String)
    suspend fun authUser(): User
}

package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.domain.model.user.User

interface UserRepository {
    suspend fun requestActivateUser(code: String)
    suspend fun requestAuthUser()
    fun getCachedUser(): User
}

package ru.yofik.athena.common.domain.repositories

interface UserRepository {
    suspend fun activateUser(code: String): Boolean
}

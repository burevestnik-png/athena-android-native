package ru.yofik.athena.common.data.cache

import ru.yofik.athena.common.data.cache.model.CachedUser

interface Cache {
    suspend fun storeUsers(users: List<CachedUser>)
    suspend fun getUsers(): List<CachedUser>
}

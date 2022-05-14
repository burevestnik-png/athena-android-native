package ru.yofik.athena.common.data.cache

import ru.yofik.athena.common.data.cache.dao.UsersDao
import ru.yofik.athena.common.data.cache.model.CachedUser
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val usersDao: UsersDao
): Cache {
    override suspend fun storeUsers(users: List<CachedUser>) {
        usersDao.insert(users)
    }

    override suspend fun getUsers(): List<CachedUser> {
        return usersDao.getAll()
    }
}
package ru.yofik.athena.common.data.cache

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import ru.yofik.athena.common.data.cache.dao.ChatsDao
import ru.yofik.athena.common.data.cache.dao.UsersDao
import ru.yofik.athena.common.data.cache.model.CachedChatAggregate
import ru.yofik.athena.common.data.cache.model.CachedUser

class RoomCache
@Inject
constructor(private val usersDao: UsersDao, private val chatsDao: ChatsDao) : Cache {
    override fun getUsers(): Flow<List<CachedUser>> {
        return usersDao.getAll()
    }

    override suspend fun deleteAllUsers() {
        usersDao.deleteAll()
    }

    override suspend fun insertUsers(users: List<CachedUser>) {
        usersDao.insert(users)
    }

    override fun getChats(): Flow<List<CachedChatAggregate>> {
        return chatsDao.getAll()
    }

    override suspend fun insertChats(chatsAggregate: List<CachedChatAggregate>) {
        chatsDao.insertChats(chatsAggregate)
    }
}

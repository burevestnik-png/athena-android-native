package ru.yofik.athena.common.data.cache

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import ru.yofik.athena.common.data.cache.dao.ChatsDao
import ru.yofik.athena.common.data.cache.dao.UsersDao
import ru.yofik.athena.common.data.cache.model.CachedChatAggregate
import ru.yofik.athena.common.data.cache.model.CachedChatUserCrossRef
import ru.yofik.athena.common.data.cache.model.CachedUser
import timber.log.Timber

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
        // todo delete on eacg
        return chatsDao.getAll().onEach { Timber.d("getChats: ${it.joinToString("\n")}") }
    }

    override suspend fun deleteAllChats() {
        chatsDao.deleteAll()
    }

    override suspend fun insertChats(chatsAggregate: List<CachedChatAggregate>) {
        chatsDao.insertChats(chatsAggregate)

        chatsAggregate.forEach { chatAggregate ->
            chatAggregate.users.forEach { user ->
                chatsDao.insertCachedChatUserCrossRef(
                    CachedChatUserCrossRef(chatAggregate.chat.chatId, user.userId)
                )
            }
        }
    }
}

package ru.yofik.athena.common.data.cache

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.yofik.athena.common.data.cache.dao.ChatsDao
import ru.yofik.athena.common.data.cache.dao.MessageDao
import ru.yofik.athena.common.data.cache.dao.UsersDao
import ru.yofik.athena.common.data.cache.model.CachedChatAggregate
import ru.yofik.athena.common.data.cache.model.CachedMessage
import ru.yofik.athena.common.data.cache.model.CachedUser
import javax.inject.Inject

internal class RoomCache
@Inject
constructor(
    private val usersDao: UsersDao,
    private val chatsDao: ChatsDao,
    private val messageDao: MessageDao,
) : Cache {

    ///////////////////////////////////////////////////////////////////////////
    // User
    ///////////////////////////////////////////////////////////////////////////

    override fun getUsers(): Flow<List<CachedUser>> {
        return usersDao.getAll()
    }

    override suspend fun deleteAllUsers() {
        usersDao.deleteAllUsers()
    }

    override suspend fun insertUsers(users: List<CachedUser>) {
        usersDao.insertUsers(users)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Chat
    ///////////////////////////////////////////////////////////////////////////

    override fun getChats(): Flow<List<CachedChatAggregate>> {
        return chatsDao.getAll()
    }

    override suspend fun getChat(id: Long): CachedChatAggregate {
        return chatsDao.getById(id)
    }

    override suspend fun deleteAllChats() {
        chatsDao.deleteAllChats()
    }

    override suspend fun insertChats(chatsAggregates: List<CachedChatAggregate>) {
        chatsDao.insertChatAggregates(chatsAggregates)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Message
    ///////////////////////////////////////////////////////////////////////////

    override fun getAllMessagesByChatId(chatId: Long): Flow<List<CachedMessage>> {
        return messageDao.getAllFromDefiniteChat(chatId).map { messages ->
            messages.filter { it.chatId == chatId }
        }
    }

    override suspend fun insertMessage(message: CachedMessage) {
        messageDao.insertMessage(message)
    }

    override suspend fun deleteAllMessages() {
        TODO("Not yet implemented")
    }

    override suspend fun cleanup() {
        chatsDao.deleteAll()
        messageDao.deleteAll()
        usersDao.deleteAll()
    }
}

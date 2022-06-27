package ru.yofik.athena.common.data.cache

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.yofik.athena.common.data.cache.dao.ChatsDao
import ru.yofik.athena.common.data.cache.dao.MessageDao
import ru.yofik.athena.common.data.cache.dao.UsersDao
import ru.yofik.athena.common.data.cache.model.CachedChatAggregate
import ru.yofik.athena.common.data.cache.model.CachedChatUserCrossRef
import ru.yofik.athena.common.data.cache.model.CachedMessage
import ru.yofik.athena.common.data.cache.model.CachedUser
import timber.log.Timber

class RoomCache
@Inject
constructor(
    private val usersDao: UsersDao,
    private val chatsDao: ChatsDao,
    private val messageDao: MessageDao
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
        return chatsDao.getAll().onEach { Timber.d("getChats: ${it.joinToString("\n")}") }
    }

    override suspend fun getChat(id: Long): CachedChatAggregate {
        return chatsDao.getById(id)
    }

    override suspend fun deleteAllChats() {
        chatsDao.deleteAllChats()
    }

    override suspend fun insertChats(chatsAggregate: List<CachedChatAggregate>) {
        chatsAggregate.forEach { chatAggregate ->
            chatsDao.insertChat(chatAggregate.chat)
            usersDao.insertUsers(chatAggregate.users)

            if (chatAggregate.lastMessage != null) {
                messageDao.insertMessage(chatAggregate.lastMessage)
            }

            chatAggregate.users.forEach { user ->
                chatsDao.insertCachedChatUserCrossRef(
                    CachedChatUserCrossRef(chatAggregate.chat.chatId, user.userId)
                )
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Message
    ///////////////////////////////////////////////////////////////////////////

    override fun getAllMessagesFromDefiniteChat(chatId: Long): Flow<List<CachedMessage>> {
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

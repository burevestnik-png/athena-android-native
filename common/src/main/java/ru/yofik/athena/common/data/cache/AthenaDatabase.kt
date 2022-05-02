package ru.yofik.athena.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.yofik.athena.common.data.cache.dao.ChatsDao
import ru.yofik.athena.common.data.cache.dao.MessagesDao
import ru.yofik.athena.common.data.cache.dao.UsersDao

@Database(
    entities = [],
    version = 1
)
abstract class AthenaDatabase : RoomDatabase() {
    abstract fun chatsDao(): ChatsDao
    abstract fun messagesDao(): MessagesDao
    abstract fun usersDao() : UsersDao
}
package ru.yofik.athena.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.yofik.athena.common.data.cache.converters.ChatTypeConverter
import ru.yofik.athena.common.data.cache.converters.LocalDateTimeConverter
import ru.yofik.athena.common.data.cache.dao.ChatsDao
import ru.yofik.athena.common.data.cache.dao.MessageDao
import ru.yofik.athena.common.data.cache.dao.UsersDao
import ru.yofik.athena.common.data.cache.model.*

@Database(
    entities =
        [
            CachedUser::class,
            CachedChat::class,
            CachedMessage::class,
            CachedChatUserCrossRef::class,
            CachedChatLastMessageCrossRef::class
        ],
    version = 17,
    exportSchema = false
)
@TypeConverters(LocalDateTimeConverter::class, ChatTypeConverter::class)
internal abstract class AthenaDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
    abstract fun chatsDao(): ChatsDao
    abstract fun messageDao(): MessageDao
}

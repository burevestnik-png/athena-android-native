package ru.yofik.athena.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.yofik.athena.common.data.cache.converters.LocalDateTimeConverter
import ru.yofik.athena.common.data.cache.dao.ChatsDao
import ru.yofik.athena.common.data.cache.dao.UsersDao
import ru.yofik.athena.common.data.cache.model.CachedChat
import ru.yofik.athena.common.data.cache.model.CachedChatUserCrossRef
import ru.yofik.athena.common.data.cache.model.CachedMessage
import ru.yofik.athena.common.data.cache.model.CachedUser

@Database(
    entities =
        [CachedUser::class, CachedChat::class, CachedChatUserCrossRef::class, CachedMessage::class],
    version = 1
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AthenaDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
    abstract fun chatsDao(): ChatsDao
}

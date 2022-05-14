package ru.yofik.athena.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.yofik.athena.common.data.cache.dao.UsersDao
import ru.yofik.athena.common.data.cache.model.CachedUser

@Database(entities = [CachedUser::class], version = 1)
abstract class AthenaDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}

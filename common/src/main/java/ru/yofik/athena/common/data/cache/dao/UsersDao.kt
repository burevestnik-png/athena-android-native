package ru.yofik.athena.common.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.yofik.athena.common.data.cache.model.CachedUser

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(users: List<CachedUser>)

    @Query("SELECT * FROM users") suspend fun getAll(): List<CachedUser>

    @Query("DELETE FROM users") suspend fun deleteAll()
}

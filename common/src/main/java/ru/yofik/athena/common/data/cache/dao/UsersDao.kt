package ru.yofik.athena.common.data.cache.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.yofik.athena.common.data.cache.model.CachedUser

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(users: List<CachedUser>)

    @Transaction @Query("SELECT * FROM users") fun getAll(): Flow<List<CachedUser>>

    @Transaction @Query("DELETE FROM users") suspend fun deleteAll()
}

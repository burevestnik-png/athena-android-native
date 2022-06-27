package ru.yofik.athena.common.data.cache.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.yofik.athena.common.data.cache.model.CachedUser

@Dao
abstract class UsersDao {

    ///////////////////////////////////////////////////////////////////////////
    // INSERT
    ///////////////////////////////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertUsers(users: List<CachedUser>)

    ///////////////////////////////////////////////////////////////////////////
    // QUERY
    ///////////////////////////////////////////////////////////////////////////

    @Transaction @Query("SELECT * FROM users")
    abstract fun getAll(): Flow<List<CachedUser>>

    ///////////////////////////////////////////////////////////////////////////
    // DELETE
    ///////////////////////////////////////////////////////////////////////////

    suspend fun deleteAll() {
        deleteAllUsers()
    }

    @Transaction @Query("DELETE FROM users") abstract suspend fun deleteAllUsers()
}

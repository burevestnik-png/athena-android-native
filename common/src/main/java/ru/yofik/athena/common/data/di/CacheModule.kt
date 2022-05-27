package ru.yofik.athena.common.data.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import ru.yofik.athena.common.data.cache.AthenaDatabase
import ru.yofik.athena.common.data.cache.Cache
import ru.yofik.athena.common.data.cache.MIGRATION_1_2
import ru.yofik.athena.common.data.cache.RoomCache
import ru.yofik.athena.common.data.cache.dao.ChatsDao
import ru.yofik.athena.common.data.cache.dao.MessageDao
import ru.yofik.athena.common.data.cache.dao.UsersDao

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CacheModule {

    @Binds abstract fun bindCache(cache: RoomCache): Cache

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context,
        ): AthenaDatabase {
            return Room.databaseBuilder(context, AthenaDatabase::class.java, "athena.db")
                .addMigrations(MIGRATION_1_2)
                .build()
        }

        @Provides
        fun provideUsersDao(athenaDatabase: AthenaDatabase): UsersDao {
            return athenaDatabase.usersDao()
        }

        @Provides
        fun provideChatsDao(athenaDatabase: AthenaDatabase): ChatsDao {
            return athenaDatabase.chatsDao()
        }

        @Provides
        fun provideMessageDao(athenaDatabase: AthenaDatabase): MessageDao {
            return athenaDatabase.messageDao()
        }
    }
}

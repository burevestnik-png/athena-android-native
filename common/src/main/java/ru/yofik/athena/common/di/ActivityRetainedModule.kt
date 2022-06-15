package ru.yofik.athena.common.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import ru.yofik.athena.common.data.repositories.*
import ru.yofik.athena.common.domain.repositories.*

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindUserRepository(repository: UserProfileRepositoryImpl): UserProfileRepository

    @Binds
    @ActivityRetainedScoped
    abstract fun bindChatRepository(repository: ChatRepositoryImpl): ChatRepository

    @Binds
    @ActivityRetainedScoped
    abstract fun bindNotificationRepository(
        repository: NotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    @ActivityRetainedScoped
    abstract fun bindCurrentUserRepository(
        repository: UserRepositoryImpl
    ): UserRepository

    @Binds
    @ActivityRetainedScoped
    abstract fun bindMessageRepository(repository: MessageRepositoryImpl): MessageRepository

    @Binds
    @ActivityRetainedScoped
    abstract fun bindCommonRepository(repository: CommonRepositoryImpl): CommonRepository
}

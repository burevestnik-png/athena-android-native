package ru.yofik.athena.common.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.yofik.athena.common.data.preferences.MessengerPreferences
import ru.yofik.athena.common.data.preferences.Preferences

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PreferencesModule {
    @Binds
    abstract fun providePreferences(preferences: MessengerPreferences): Preferences
}

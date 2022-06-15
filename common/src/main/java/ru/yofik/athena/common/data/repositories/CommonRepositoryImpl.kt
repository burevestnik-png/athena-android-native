package ru.yofik.athena.common.data.repositories

import javax.inject.Inject
import ru.yofik.athena.common.data.cache.Cache
import ru.yofik.athena.common.data.preferences.Preferences
import ru.yofik.athena.common.domain.repositories.CommonRepository

class CommonRepositoryImpl
@Inject
constructor(private val cache: Cache, private val preferences: Preferences) : CommonRepository {
    override suspend fun removeAllCache() {
        cache.cleanup()
        preferences.removeCurrentUser()
        preferences.removeAccessToken()
    }
}

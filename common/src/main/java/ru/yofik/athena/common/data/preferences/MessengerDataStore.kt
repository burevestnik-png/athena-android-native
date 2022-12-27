package ru.yofik.athena.common.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDateTime
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_ACCESS_TOKEN
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_EXPIRES_IN
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_REFRESH_TOKEN
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_USER_EMAIL
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_USER_ID
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_USER_LOGIN
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_USER_ROLE
import ru.yofik.athena.common.domain.model.users.Role
import ru.yofik.athena.common.domain.model.users.Tokens
import ru.yofik.athena.common.domain.model.users.UserV2

private val Context.dataStore by preferencesDataStore("common")

class MessengerDataStore @Inject constructor(@ApplicationContext context: Context) : Preferences {
    private val dataStore = context.dataStore

    override suspend fun putTokens(tokens: Tokens) {
        dataStore.edit {
            it[stringPreferencesKey(KEY_ACCESS_TOKEN)] = tokens.accessToken
            it[stringPreferencesKey(KEY_REFRESH_TOKEN)] = tokens.refreshToken
            it[longPreferencesKey(KEY_EXPIRES_IN)] = tokens.expiresIn
        }
    }

    override suspend fun getTokens(): Tokens =
        dataStore.data
            .map {
                Tokens(
                    accessToken = it[stringPreferencesKey(KEY_ACCESS_TOKEN)] ?: "",
                    refreshToken = it[stringPreferencesKey(KEY_REFRESH_TOKEN)] ?: "",
                    expiresIn = it[longPreferencesKey(KEY_EXPIRES_IN)] ?: -1
                )
            }
            .first()

    override suspend fun removeTokens() {
        dataStore.edit {
            it.remove(stringPreferencesKey(KEY_ACCESS_TOKEN))
            it.remove(stringPreferencesKey(KEY_REFRESH_TOKEN))
            it.remove(longPreferencesKey(KEY_EXPIRES_IN))
        }
    }

    override suspend fun putCurrentUser(user: UserV2): Unit =
        with(user) {
            dataStore.edit {
                it[longPreferencesKey(KEY_USER_ID)] = id
                it[stringPreferencesKey(KEY_USER_LOGIN)] = login
                it[stringPreferencesKey(KEY_USER_EMAIL)] = email
                it[stringPreferencesKey(KEY_USER_ROLE)] = role.toString()
            }
        }

    override suspend fun getCurrentUser(): UserV2 =
        dataStore.data
            .map {
                UserV2(
                    id = it[longPreferencesKey(KEY_USER_ID)] ?: -1,
                    email = it[stringPreferencesKey(KEY_USER_LOGIN)] ?: "",
                    login = it[stringPreferencesKey(KEY_USER_EMAIL)] ?: "",
                    role = Role.valueOf(it[stringPreferencesKey(KEY_USER_ROLE)] ?: "USER"),
                    isLocked = false,
                    lockReason = "",
                    credentialsExpirationDate = LocalDateTime.now(),
                    lastLoginDate = LocalDateTime.now()
                )
            }
            .first()

    override suspend fun removeCurrentUser() {
        dataStore.edit {
            it.remove(longPreferencesKey(KEY_USER_ID))
            it.remove(stringPreferencesKey(KEY_USER_LOGIN))
            it.remove(stringPreferencesKey(KEY_USER_EMAIL))
            it.remove(stringPreferencesKey(KEY_USER_ROLE))
        }
    }
}

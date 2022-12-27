package ru.yofik.athena.common.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_ACCESS_TOKEN
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_EXPIRES_IN
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_REFRESH_TOKEN
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_USER_ID
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_USER_LOGIN
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_USER_NAME
import ru.yofik.athena.common.domain.model.users.Tokens
import ru.yofik.athena.common.domain.model.users.User

class MessengerPreferences @Inject constructor(@ApplicationContext context: Context) : Preferences {
    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun putTokens(tokens: Tokens) = edit {
        putString(KEY_ACCESS_TOKEN, tokens.accessToken)
        putString(KEY_REFRESH_TOKEN, tokens.refreshToken)
        putLong(KEY_EXPIRES_IN, tokens.expiresIn)
    }

    override fun getTokens(): Tokens =
        Tokens(
            accessToken = preferences.getString(KEY_ACCESS_TOKEN, "").orEmpty(),
            refreshToken = preferences.getString(KEY_REFRESH_TOKEN, "").orEmpty(),
            expiresIn = preferences.getLong(KEY_EXPIRES_IN, -1)
        )

    override fun removeTokens() = edit {
        remove(KEY_ACCESS_TOKEN)
        remove(KEY_REFRESH_TOKEN)
        remove(KEY_EXPIRES_IN)
    }

    override fun putCurrentUser(user: User) {
        edit {
            putLong(KEY_USER_ID, user.id)
            putString(KEY_USER_LOGIN, user.login)
            putString(KEY_USER_NAME, user.name)
        }
    }

    override fun getCurrentUser(): User {
        return User(
            id = getCurrentUserId(),
            name = getCurrentUserName(),
            login = getCurrentUserLogin()
        )
    }

    override fun removeCurrentUser() {
        edit {
            remove(KEY_USER_ID)
            remove(KEY_USER_NAME)
            remove(KEY_USER_LOGIN)
        }
    }

    override fun getCurrentUserId(): Long {
        return preferences.getLong(KEY_USER_ID, -1)
    }

    override fun getCurrentUserLogin(): String {
        return preferences.getString(KEY_USER_LOGIN, "").orEmpty()
    }

    override fun getCurrentUserName(): String {
        return preferences.getString(KEY_USER_NAME, "").orEmpty()
    }

    private inline fun edit(block: SharedPreferences.Editor.() -> Unit) {
        with(preferences.edit()) {
            block()
            commit()
        }
    }

    companion object {
        const val PREFERENCES_NAME = "MESSENGER_PREFERENCES"
    }
}

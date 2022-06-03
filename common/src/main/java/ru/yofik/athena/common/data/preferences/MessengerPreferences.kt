package ru.yofik.athena.common.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_ACCESS_TOKEN
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_USER_ID
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_USER_LOGIN
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_USER_NAME
import ru.yofik.athena.common.domain.model.users.User
import javax.inject.Inject

class MessengerPreferences @Inject constructor(@ApplicationContext context: Context) : Preferences {
    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun putAccessToken(accessToken: String) {
        edit { putString(KEY_ACCESS_TOKEN, accessToken) }
    }

    override fun getAccessToken(): String {
        return preferences.getString(KEY_ACCESS_TOKEN, "").orEmpty()
    }

    override fun removeAccessToken() {
        edit { remove(KEY_ACCESS_TOKEN) }
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

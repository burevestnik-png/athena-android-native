package ru.yofik.athena.common.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import javax.inject.Inject
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

    override fun putCurrentUser(user: UserV2) =
        with(user) {
            edit {
                putLong(KEY_USER_ID, id)
                putString(KEY_USER_LOGIN, login)
                putString(KEY_USER_EMAIL, email)
                putString(KEY_USER_ROLE, role.toString())
            }
        }

    override fun getCurrentUser(): UserV2 =
        with(preferences) {
            UserV2(
                id = getLong(KEY_USER_ID, -1),
                email = getString(KEY_USER_LOGIN, "").orEmpty(),
                login = getString(KEY_USER_EMAIL, "").orEmpty(),
                role = Role.valueOf(getString(KEY_USER_ROLE, "USER") ?: "ROLE"),
                isLocked = false,
                lockReason = "",
                credentialsExpirationDate = LocalDateTime.now(),
                lastLoginDate = LocalDateTime.now()
            )
        }

    override fun removeCurrentUser() {
        edit {
            remove(KEY_USER_ID)
            remove(KEY_USER_EMAIL)
            remove(KEY_USER_LOGIN)
            remove(KEY_USER_ROLE)
        }
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

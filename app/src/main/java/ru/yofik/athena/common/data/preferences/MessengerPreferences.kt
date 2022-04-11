package ru.yofik.athena.common.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import ru.yofik.athena.common.data.preferences.PreferencesConstants.KEY_ACCESS_TOKEN

class MessengerPreferences @Inject constructor(@ApplicationContext context: Context) : Preferences {
    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun putAccessToken(accessToken: String) {
        edit { putString(KEY_ACCESS_TOKEN, accessToken) }
    }

    override fun getAccessToken(): String {
        return preferences.getString(KEY_ACCESS_TOKEN, "").orEmpty()
    }

    override fun deleteAccessToken() {
        edit {
            remove(KEY_ACCESS_TOKEN)
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

package ru.yofik.athena.common.data.preferences

import ru.yofik.athena.common.domain.model.user.User

interface Preferences {
    fun putAccessToken(accessToken: String)
    fun getAccessToken(): String
    fun removeAccessToken()

    fun putCurrentUser(user: User)
    fun removeCurrentUser()
    fun getCurrentUser(): User
    fun getCurrentUserId(): Long
    fun getCurrentUserLogin(): String
    fun getCurrentUserName(): String
}

package ru.yofik.athena.common.data.preferences

import ru.yofik.athena.common.domain.model.user.User

interface Preferences {
    fun putAccessToken(accessToken: String)
    fun getAccessToken(): String
    fun deleteAccessToken()

    fun putCurrentUser(user: User)
    fun getCurrentUser(): User
    fun deleteCurrentUser()
    fun getUserId(): Long
    fun getUserLogin(): String
    fun getUserName(): String
}

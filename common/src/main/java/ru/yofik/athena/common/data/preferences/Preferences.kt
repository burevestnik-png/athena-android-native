package ru.yofik.athena.common.data.preferences

import ru.yofik.athena.common.domain.model.user.User

interface Preferences {
    fun putAccessToken(accessToken: String)
    fun getAccessToken(): String
    fun deleteAccessToken()

    fun putUserInfo(user: User)
    fun getUser(): User
    fun deleteUserInfo()
}

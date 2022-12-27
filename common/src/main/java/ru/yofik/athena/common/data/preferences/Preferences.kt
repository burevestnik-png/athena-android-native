package ru.yofik.athena.common.data.preferences

import ru.yofik.athena.common.domain.model.users.Tokens
import ru.yofik.athena.common.domain.model.users.User

interface Preferences {
    fun putTokens(tokens: Tokens)
    fun getTokens(): Tokens
    fun removeTokens()

    fun putCurrentUser(user: User)
    fun removeCurrentUser()
    fun getCurrentUser(): User
    fun getCurrentUserId(): Long
    fun getCurrentUserLogin(): String
    fun getCurrentUserName(): String
}

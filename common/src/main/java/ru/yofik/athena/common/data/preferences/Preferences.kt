package ru.yofik.athena.common.data.preferences

import ru.yofik.athena.common.domain.model.users.Tokens
import ru.yofik.athena.common.domain.model.users.User
import ru.yofik.athena.common.domain.model.users.UserV2

interface Preferences {
    fun putTokens(tokens: Tokens)
    fun getTokens(): Tokens
    fun removeTokens()

    fun putCurrentUser(user: UserV2)
    fun getCurrentUser(): UserV2
    fun removeCurrentUser()
}

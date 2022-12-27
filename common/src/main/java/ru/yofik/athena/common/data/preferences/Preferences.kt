package ru.yofik.athena.common.data.preferences

import ru.yofik.athena.common.domain.model.users.Tokens
import ru.yofik.athena.common.domain.model.users.UserV2

interface Preferences {
    suspend fun putTokens(tokens: Tokens)
    suspend fun getTokens(): Tokens
    suspend fun removeTokens()

    suspend fun putCurrentUser(user: UserV2)
    suspend fun getCurrentUser(): UserV2
    suspend fun removeCurrentUser()
}

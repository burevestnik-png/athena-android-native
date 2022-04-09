package ru.yofik.athena.common.domain.model.user

import java.util.*

/** Basic entity object */
data class User(
    val id: Int,
    val name: String,
    val login: String
) {
    companion object {
        // TODO remove in future
        fun getPredefinedUsers(): List<User> =
            listOf(
                User(id = 1, name = "Yarik", login = "Login1"),
                User(id = 2, name = "Lesha", login = "Login2"),
            )

        fun getLesha() = getPredefinedUsers()[1]

        fun getYarik() = getPredefinedUsers()[0]

    }
}

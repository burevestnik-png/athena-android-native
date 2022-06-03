package ru.yofik.athena.common.domain.model.users

/** Basic entity object which represents user who logged in messenger */
data class User(
    val id: Long,
    val name: String,
    val login: String,
)

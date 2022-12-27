package ru.yofik.athena.common.domain.model.users

data class Tokens(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long
)
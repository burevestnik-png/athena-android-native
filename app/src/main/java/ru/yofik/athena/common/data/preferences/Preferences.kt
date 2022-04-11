package ru.yofik.athena.common.data.preferences

interface Preferences {
    fun putAccessToken(accessToken: String)
    fun getAccessToken(): String
    fun deleteAccessToken()
}

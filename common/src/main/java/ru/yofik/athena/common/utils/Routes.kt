package ru.yofik.athena.common.utils

object Routes {
    private const val DOMAIN = "athena://"

    const val CHAT_LIST = "${DOMAIN}chatList"
    const val CREATE_CHAT = "${DOMAIN}createChat"
    const val LOGIN = "${DOMAIN}login"

    @Suppress("functionName")
    fun CHAT(chatId: Long): String {
        return "${DOMAIN}chat/?${addQueryParameter("id", chatId.toString())}"
    }

    private fun addQueryParameter(key: String, value: String): String {
        return "$key=$value"
    }
}

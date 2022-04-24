package ru.yofik.athena.common.utils

import android.net.Uri
import androidx.core.net.toUri

object InternalDeepLink {
    private const val DOMAIN = "athena://"

    const val CHAT_LIST = "${DOMAIN}chatList"
    const val CREATE_CHAT = "${DOMAIN}createChat"
    const val LOGIN = "${DOMAIN}login"

    fun createChatDeepLink(chatId: Long): Uri {
        return "${DOMAIN}chat/?${addQueryParameter("id", chatId.toString())}".toUri()
    }

    private fun addQueryParameter(key: String, value: String): String {
        return "$key=$value"
    }
}

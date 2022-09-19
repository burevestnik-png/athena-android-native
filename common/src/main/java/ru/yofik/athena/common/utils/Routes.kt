package ru.yofik.athena.common.utils

object Routes {
    private const val DOMAIN = "athena://"

    const val CHAT_LIST = "chatList"
    const val CREATE_CHAT = "createChat"
    const val LOGIN = "login"
    const val CHAT = "chat"

    @Suppress("functionName")
    fun CHAT(chatId: Long): String {
        return "${DOMAIN}chat/?${addQueryParameter("id", chatId.toString())}"
    }

    private fun addQueryParameter(key: String, value: String): String {
        return "$key=$value"
    }
}

class Route(val path: String) {
    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        private val domain = "athena://"
        private val queryParams: MutableMap<String, String> = mutableMapOf()

        var screen: String = ""

        fun addQueryParam(key: String, value: String) {
            queryParams[key] = value
        }

        fun build(): Route {
            val path =
                StringBuilder().apply {
                    append(domain)
                    append(screen)
                }

            if (queryParams.isNotEmpty()) {
                path.append("/?")
                for ((key, value) in queryParams) {
                    path.append("$key=$value")
                }
            }

            return Route(path.toString())
        }
    }
}

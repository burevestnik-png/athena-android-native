package ru.yofik.athena.common.data.api.ws.model

enum class Command(val content: String) {
    SUBSCRIBE_ON_NOTIFICATIONS("SUBSCRIBE_ON_NOTIFICATIONS"),
    RECEIVE_NOTIFICATION("RECEIVE_NOTIFICATION")
}

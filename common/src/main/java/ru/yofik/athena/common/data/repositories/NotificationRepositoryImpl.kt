package ru.yofik.athena.common.data.repositories

import okhttp3.WebSocket
import ru.yofik.athena.common.domain.repositories.NotificationRepository
import timber.log.Timber
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationWebSocket: WebSocket
): NotificationRepository {
    override fun startNotificationChannel() {
        val result = notificationWebSocket.send("{\"command\":\"SUBSCRIBE_ON_NOTIFICATIONS\"}")
        Timber.d("In repository result: $result")
        Timber.d("startNotificationChannel: $notificationWebSocket")
    }
}
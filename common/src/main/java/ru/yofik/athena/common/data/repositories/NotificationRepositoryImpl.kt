package ru.yofik.athena.common.data.repositories

import okhttp3.WebSocket
import ru.yofik.athena.common.domain.repositories.NotificationRepository
import timber.log.Timber
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationWebSocket: WebSocket
): NotificationRepository {
    override fun startNotificationChannel() {
        Timber.d("startNotificationChannel: $notificationWebSocket")
    }
}
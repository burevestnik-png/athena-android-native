package ru.yofik.athena.common.data.api.ws.listeners

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import ru.yofik.athena.common.data.api.ws.RxNotificationEvent
import ru.yofik.athena.common.data.api.ws.RxNotificationPublisher
import ru.yofik.athena.common.data.api.ws.model.mappers.MessageNotificationMapper
import timber.log.Timber
import javax.inject.Inject

class NotificationListener
@Inject
constructor(private val messageNotificationMapper: MessageNotificationMapper) :
    WebSocketListener() {
    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Timber.d("onClosed: $code $reason ")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        Timber.d("onClosing: $code $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Timber.d("onFailure: ${t.message}")
        Timber.d("onFailure: $response")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Timber.d("onMessage text: $text")

        RxNotificationPublisher.publish(
            RxNotificationEvent.NewNotification(messageNotificationMapper.mapToDomain(text))
        )
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        Timber.d("onMessage bytes: $bytes")
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Timber.d("onOpen: $response")
    }
}

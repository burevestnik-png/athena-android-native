package ru.yofik.athena.common.data.api.ws

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

// todo remade injectable
object RxNotificationPublisher {
    private val publisher = PublishSubject.create<Any>()

    fun publish(event: Any) {
        publisher.onNext(event)
    }

    fun <T> listen(eventType: Class<T>): Observable<T> {
        return publisher.ofType(eventType)
    }
}

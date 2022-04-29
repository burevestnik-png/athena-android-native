package ru.yofik.athena.common.data.api.ws.model.mappers

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import ru.yofik.athena.common.data.api.mapping.DomainMapper
import ru.yofik.athena.common.data.api.ws.model.notifications.SubscribeNotification
import javax.inject.Inject

class SubscribeNotificationMapper @Inject constructor() : DomainMapper<SubscribeNotification, String> {
    private val adapter: JsonAdapter<SubscribeNotification> =
        Moshi.Builder().build().adapter(SubscribeNotification::class.java)

    override fun mapToApi(domainModel: SubscribeNotification): String {
        return adapter.toJson(domainModel)
    }
}

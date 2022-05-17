package ru.yofik.athena.common.data.api

import ru.yofik.athena.common.data.api.ApiVersions.V1

object ApiVersions {
    const val V1 = "v1"
}

object ApiHttpConstants {
    //    const val BASE_ENDPOINT = "http://192.168.0.13:8080/api/"
    const val BASE_ENDPOINT = "http://10.0.2.2:8080/api/"

    const val ACTIVATE_ENDPOINT = "$V1/users/activation"
    const val AUTHORIZATION_ENDPOINT = "$V1/users/authorization"

    const val ALL_USERS_ENDPOINT = "$V1/userProfiles"

    const val CHATS_ENDPOINT = "$V1/chats"
}

object ApiWsConstants {
    //    const val WS_BASE_ENDPOINT = "ws://192.168.0.13:8080/ws-api/"
    private const val WS_BASE_ENDPOINT = "ws://10.0.2.2:8080/ws-api/"

    const val WS_NOTIFICATION_ENDPOINT = "$WS_BASE_ENDPOINT$V1/notifications"
}

object ApiParameters {
    const val TOKEN_TYPE = "Bearer"
    const val AUTH_HEADER = "Authorization"
    const val NO_AUTH_HEADER = "no-auth"
    const val NO_AUTH_HEADER_FULL = "no-auth: "
}

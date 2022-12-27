package ru.yofik.athena.common.data.api

import retrofit2.http.Headers
import ru.yofik.athena.common.data.api.ApiVersions.V1
import ru.yofik.athena.common.data.api.ApiVersions.V2

internal object ApiVersions {
    const val V1 = "v1"
    const val V2 = "v2"
}

internal object ApiHttpConstants {
    // TODO move to localProps
    const val BASE_URL_AUTH_SERVICE = "http://10.0.2.2:8081/api/"
    const val BASE_URL_MESSENGER_SERVICE = "http://10.0.2.2:8080/api/"

    const val ALL_USERS_ENDPOINT = "$V1/userProfiles"

    const val CHATS_ENDPOINT = "$V1/chats"
}

internal object ApiWsConstants {
    private const val WS_BASE_ENDPOINT = "ws://10.0.2.2:8080/ws-api/"

    const val WS_NOTIFICATION_ENDPOINT = "$WS_BASE_ENDPOINT$V1/notifications"
}

internal object ApiParameters {
    const val TOKEN_TYPE = "Bearer"
    const val AUTH_HEADER = "Authorization"
    const val NO_AUTH_HEADER = "no-auth"
    const val NO_AUTH_HEADER_FULL = "no-auth: "
}

internal object AuthServiceEndpoints {
    const val SIGN_IN_USER = "$V2/auth/users/sign-in"
    const val SIGN_OUT_USER = "$V2/auth/users/sign-out"
    const val REFRESH_USER_ACCESS = "$V2/auth/users/access/refresh"

    const val GET_CURRENT_USER = "$V2/users/my"
}

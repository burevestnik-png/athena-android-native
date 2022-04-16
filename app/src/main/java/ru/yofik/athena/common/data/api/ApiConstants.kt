package ru.yofik.athena.common.data.api

object ApiConstants {
    const val BASE_ENDPOINT = "http://10.0.2.2:8080"

    private const val V1 = "/api/v1"
    const val ACTIVATE_ENDPOINT = "$V1/users/activation"
    const val AUTHORIZATION_ENDPOINT = "$V1/users/authorization"

    const val ALL_USERS_ENDPOINT = "$V1/userProfiles"

    const val CHATS_ENDPOINT = "$V1/chats"

    const val CLIENT_TOKEN =
        "eyJhbGciOiJBMTI4S1ciLCJlbmMiOiJBMTI4Q0JDLUhTMjU2In0.9hySVyyM-nhE5E41Y48LTiaiTr-of2Xl-Khr_EqvZcBPEt7Kjzj3Sw.xeNpU7r7PDj2RFSuC6Ncrw.243VkwN2Y4wGM6f0qLHGXtoaoyFiHNvj2UfG7TSSQTxD0jFBGKPW79B5dRYOd-VeHglmnXcsNaqqpjCQdJiNtH_sHGvaX8c7e6BGk2Y3uLaOcX106cEGkJtEUhe5BqzW.OLeLKzI173aoAsYujY5U6g"
}

object ApiParameters {
    const val TOKEN_TYPE = "Bearer"
    const val AUTH_HEADER = "Authorization"
    const val NO_AUTH_HEADER = "no-auth"
    const val NO_AUTH_HEADER_FULL = "no-auth: "
}

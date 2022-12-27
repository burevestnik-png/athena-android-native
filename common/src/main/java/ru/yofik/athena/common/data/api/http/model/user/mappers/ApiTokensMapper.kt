package ru.yofik.athena.common.data.api.http.model.user.mappers

import ru.yofik.athena.common.data.api.common.ApiMapper
import ru.yofik.athena.common.data.api.common.MappingException
import ru.yofik.athena.common.data.api.http.model.user.apiEntity.ApiTokens
import ru.yofik.athena.common.domain.model.users.Tokens
import ru.yofik.athena.common.utils.getOrThrowMappingException
import javax.inject.Inject

class ApiTokensMapper @Inject constructor() : ApiMapper<ApiTokens, Tokens> {
    override fun mapToDomain(apiEntity: ApiTokens?): Tokens =
        apiEntity?.let {
            Tokens(
                accessToken = apiEntity.accessToken.getOrThrowMappingException("accessToken"),
                refreshToken = apiEntity.refreshToken.getOrThrowMappingException("refreshToken"),
                expiresIn = apiEntity.expiresIn.getOrThrowMappingException("expiresIn")
            )
        }
            ?: throw MappingException("ApiTokens cant be null")
}

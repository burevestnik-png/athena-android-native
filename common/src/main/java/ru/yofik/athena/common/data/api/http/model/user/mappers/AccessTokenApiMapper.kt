package ru.yofik.athena.common.data.api.http.model.user.mappers

import javax.inject.Inject
import ru.yofik.athena.common.data.api.mapping.ApiMapper
import ru.yofik.athena.common.data.api.mapping.MappingException
import ru.yofik.athena.common.data.api.http.model.user.responses.dto.AccessTokenDto

class AccessTokenApiMapper @Inject constructor() : ApiMapper<AccessTokenDto, String> {
    override fun mapToDomain(entityDTO: AccessTokenDto?): String {
        return entityDTO?.accessToken
            ?: throw MappingException("Invalid token from server ${entityDTO?.accessToken}")
    }
}

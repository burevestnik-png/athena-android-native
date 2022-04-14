package ru.yofik.athena.common.data.api.model.user.mappers

import javax.inject.Inject
import ru.yofik.athena.common.data.api.model.mappers.DtoMapper
import ru.yofik.athena.common.data.api.model.mappers.MappingException
import ru.yofik.athena.common.data.api.model.user.responses.dto.AccessTokenDto

class AccessTokenDtoMapper @Inject constructor() : DtoMapper<AccessTokenDto, String> {
    override fun mapToDomain(entityDTO: AccessTokenDto?): String {
        return entityDTO?.accessToken
            ?: throw MappingException("Invalid token from server ${entityDTO?.accessToken}")
    }
}

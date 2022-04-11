package ru.yofik.athena.common.data.api.model.mappers

import ru.yofik.athena.common.data.api.model.responses.AccessTokenDTO
import javax.inject.Inject

class AccessTokenDtoMapper @Inject constructor() : DtoMapper<AccessTokenDTO, String> {
    override fun mapToDomain(entityDTO: AccessTokenDTO): String {
        return entityDTO.accessToken
            ?: throw MappingException("Invalid token from server ${entityDTO.accessToken}")
    }
}

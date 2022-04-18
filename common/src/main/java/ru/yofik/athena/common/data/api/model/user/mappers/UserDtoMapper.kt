package ru.yofik.athena.common.data.api.model.user.mappers

import javax.inject.Inject
import ru.yofik.athena.common.data.api.model.mappers.DtoMapper
import ru.yofik.athena.common.data.api.model.mappers.MappingException
import ru.yofik.athena.common.data.api.model.user.responses.dto.UserDto
import ru.yofik.athena.common.domain.model.user.User

class UserDtoMapper @Inject constructor() : DtoMapper<UserDto, User> {
    override fun mapToDomain(entityDTO: UserDto?): User {
        return User(
            id = entityDTO?.id ?: throw MappingException("Invalid id from server ${entityDTO?.id}"),
            name = entityDTO.name.orEmpty(),
            login = entityDTO.login.orEmpty()
        )
    }
}

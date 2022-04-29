package ru.yofik.athena.common.data.api.http.model.user.mappers

import javax.inject.Inject
import ru.yofik.athena.common.data.api.mapping.ApiMapper
import ru.yofik.athena.common.data.api.mapping.MappingException
import ru.yofik.athena.common.data.api.http.model.user.responses.dto.UserDto
import ru.yofik.athena.common.domain.model.user.User

class UserApiMapper @Inject constructor() : ApiMapper<UserDto, User> {
    override fun mapToDomain(entityDTO: UserDto?): User {
        return User(
            id = entityDTO?.id ?: throw MappingException("Invalid user id from server"),
            name = entityDTO.name.orEmpty(),
            login = entityDTO.login.orEmpty()
        )
    }
}

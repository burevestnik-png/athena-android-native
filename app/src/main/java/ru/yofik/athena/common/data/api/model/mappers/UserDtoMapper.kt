package ru.yofik.athena.common.data.api.model.mappers

import javax.inject.Inject
import ru.yofik.athena.common.data.api.model.UserDTO
import ru.yofik.athena.common.domain.model.user.User

class UserDtoMapper @Inject constructor() : DtoMapper<UserDTO?, User> {
    override fun mapToDomain(entityDTO: UserDTO?): User {
        return User(
            id = entityDTO?.id ?: throw MappingException("User ID cannot be null"),
            name = entityDTO.name.orEmpty(),
            login = entityDTO.login.orEmpty()
        )
    }
}

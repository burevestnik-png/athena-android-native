package ru.yofik.athena.common.data.api.model.mappers

import javax.inject.Inject
import ru.yofik.athena.common.data.api.model.responses.UserDTO
import ru.yofik.athena.common.domain.model.user.User

class UserDtoMapper @Inject constructor() : DtoMapper<UserDTO, User> {
    override fun mapToDomain(entityDTO: UserDTO): User {
        return User(
            id = entityDTO.id ?: throw MappingException("Invalid id from server ${entityDTO.id}"),
            name = entityDTO.name ?: "",
            login = entityDTO.login ?: ""
        )
    }
}

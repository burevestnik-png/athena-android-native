package ru.yofik.athena.common.data.api.http.model.user.mappers

import javax.inject.Inject
import ru.yofik.athena.common.data.api.common.ApiMapper
import ru.yofik.athena.common.data.api.common.MappingException
import ru.yofik.athena.common.data.api.http.model.user.apiEntity.ApiUser
import ru.yofik.athena.common.domain.model.user.User

class ApiUserMapper @Inject constructor() : ApiMapper<ApiUser, User> {
    override fun mapToDomain(apiEntity: ApiUser?): User {
        return User(
            id = apiEntity?.id ?: throw MappingException("Invalid user id from server"),
            name = apiEntity.name.orEmpty(),
            login = apiEntity.login.orEmpty()
        )
    }
}

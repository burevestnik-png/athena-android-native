package ru.yofik.athena.common.data.api.http.model.common.mappers

import ru.yofik.athena.common.data.api.common.ApiMapper
import ru.yofik.athena.common.data.api.common.MappingException
import ru.yofik.athena.common.data.api.http.model.common.apiEntity.ApiUser
import ru.yofik.athena.common.data.api.http.model.common.apiEntity.ApiUserV2
import ru.yofik.athena.common.domain.model.users.User
import javax.inject.Inject

class ApiUserMapper @Inject constructor() : ApiMapper<ApiUser, User> {
    override fun mapToDomain(apiEntity: ApiUser?): User {
        return User(
            id = apiEntity?.id ?: throw MappingException("Invalid user id from server"),
            name = apiEntity.name.orEmpty(),
            login = apiEntity.login.orEmpty(),
            //            isOnline = apiEntity.isOnline ?: false,
            //            lastOnlineTime = TimeUtils.parseToLocalDateTime(apiEntity.lastOnlineTime)
            )
    }
}

class ApiUserMapperV2 @Inject constructor() : ApiMapper<ApiUserV2, User> {
    override fun mapToDomain(apiEntity: ApiUserV2?): User {
        return User(
            id = apiEntity?.id ?: throw MappingException("Invalid user id from server"),
            // TODO Mapping mem
            name = apiEntity.login.orEmpty(),
            login = apiEntity.email.orEmpty(),
            //            isOnline = apiEntity.isOnline ?: false,
            //            lastOnlineTime = TimeUtils.parseToLocalDateTime(apiEntity.lastOnlineTime)
        )
    }
}

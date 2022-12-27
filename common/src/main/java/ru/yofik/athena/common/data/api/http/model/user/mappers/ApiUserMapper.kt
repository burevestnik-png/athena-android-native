package ru.yofik.athena.common.data.api.http.model.user.mappers

import javax.inject.Inject
import ru.yofik.athena.common.data.api.common.ApiMapper
import ru.yofik.athena.common.data.api.common.MappingException
import ru.yofik.athena.common.data.api.http.model.user.responses.ApiUser
import ru.yofik.athena.common.domain.model.users.UserV2
import ru.yofik.athena.common.utils.getOrThrowMappingException

class ApiUserMapper @Inject constructor() : ApiMapper<ApiUser, UserV2> {
    override fun mapToDomain(apiEntity: ApiUser?): UserV2 =
        apiEntity?.let {
            UserV2(
                id = it.id.getOrThrowMappingException("id"),
                email = it.email.getOrThrowMappingException("email"),
                login = it.login.getOrThrowMappingException("login"),
                role = it.role.getOrThrowMappingException("role"),
                isLocked = it.isLocked.getOrThrowMappingException("isLocked"),
                lockReason = it.lockReason.getOrThrowMappingException("lockReason"),
                credentialsExpirationTime =
                    it.credentialsExpirationTime.getOrThrowMappingException(
                        "credentialsExpirationTime"
                    ),
                lastLoginDate = it.lastLoginDate.getOrThrowMappingException("lastLoginDate")
            )
        }
            ?: throw MappingException("ApiUser is null")
}

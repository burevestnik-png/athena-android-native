package ru.yofik.athena.common.data.api.http.model.user.mappers

import javax.inject.Inject
import ru.yofik.athena.common.data.api.http.model.user.apiEntity.ApiAccessToken
import ru.yofik.athena.common.data.api.common.ApiMapper
import ru.yofik.athena.common.data.api.common.MappingException

class ApiAccessTokenMapper @Inject constructor() : ApiMapper<ApiAccessToken, String> {
    override fun mapToDomain(apiEntity: ApiAccessToken?): String {
        return apiEntity?.accessToken ?: throw MappingException("Null token from server")
    }
}

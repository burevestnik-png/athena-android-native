package ru.yofik.athena.common.data

import ru.yofik.athena.common.data.api.UserApi
import ru.yofik.athena.common.data.api.model.mappers.UserDtoMapper
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi,
    private val userDtoMapper: UserDtoMapper
) {
}
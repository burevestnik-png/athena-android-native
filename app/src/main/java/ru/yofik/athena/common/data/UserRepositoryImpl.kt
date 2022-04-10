package ru.yofik.athena.common.data

import javax.inject.Inject
import ru.yofik.athena.common.data.api.ApiConstants
import ru.yofik.athena.common.data.api.ApiParameters
import ru.yofik.athena.common.data.api.UserApi
import ru.yofik.athena.common.data.api.model.requests.ActivateUserRequest
import ru.yofik.athena.common.data.api.model.responses.ActivateUserResponse
import ru.yofik.athena.common.domain.model.user.User
import ru.yofik.athena.common.domain.repositories.UserRepository
import timber.log.Timber

class UserRepositoryImpl
@Inject
constructor(private val userApi: UserApi) :
    UserRepository {
    override suspend fun activateUser(code: String): ActivateUserResponse {
        val request = ActivateUserRequest(code)

        val response =
            userApi.activate(request, ApiParameters.TOKEN_TYPE + ApiConstants.CLIENT_TOKEN)

        Timber.d(response.toString())
        return response
    }
}

package ru.yofik.athena.common.data

import javax.inject.Inject
import ru.yofik.athena.common.data.api.UserApi
import ru.yofik.athena.common.data.api.model.requests.ActivateUserRequest
import ru.yofik.athena.common.data.api.model.responses.ActivateUserResponse
import ru.yofik.athena.common.data.preferences.Preferences
import ru.yofik.athena.common.domain.repositories.UserRepository
import timber.log.Timber

class UserRepositoryImpl
@Inject
constructor(private val userApi: UserApi, private val preferences: Preferences) : UserRepository {
    override suspend fun activateUser(code: String): Boolean {
        val request = ActivateUserRequest(code)
        val response: ActivateUserResponse = userApi.activate(request)
        Timber.d("response: ${response.status}")

        if (response.payload.accessToken != null) {
            preferences.putAccessToken(response.payload.accessToken)
            return true
        }

        return false
    }
}

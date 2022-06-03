package ru.yofik.athena.createchat.domain.model

import ru.yofik.athena.common.domain.model.users.User
import ru.yofik.athena.common.presentation.model.UiMapper
import javax.inject.Inject

class UiUserMapper @Inject constructor() : UiMapper<User, UiUser> {
    override fun mapToView(model: User): UiUser {
        return UiUser(
            id = model.id,
            name = model.name,
            login = model.login
        )
    }

}
package ru.yofik.athena.common.presentation.model.user

import ru.yofik.athena.common.domain.model.user.User
import ru.yofik.athena.common.presentation.model.UiMapper
import javax.inject.Inject

class UiUserMapper @Inject constructor() : UiMapper<User, UiUser> {
    override fun mapToView(model: User): UiUser {
        return UiUser(
            id = model.id,
            name = model.name
        )
    }

}
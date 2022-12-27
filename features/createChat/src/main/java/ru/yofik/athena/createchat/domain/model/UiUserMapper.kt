package ru.yofik.athena.createchat.domain.model

import ru.yofik.athena.common.domain.model.users.UserV2
import ru.yofik.athena.common.presentation.model.UiMapper
import javax.inject.Inject

class UiUserMapper @Inject constructor() : UiMapper<UserV2, UiUser> {
    override fun mapToView(model: UserV2): UiUser {
        return UiUser(id = model.id, name = model.email, login = model.login)
    }
}

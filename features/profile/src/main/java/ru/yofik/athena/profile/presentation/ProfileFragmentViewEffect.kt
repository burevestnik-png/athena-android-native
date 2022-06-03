package ru.yofik.athena.profile.presentation

import ru.yofik.athena.common.domain.model.users.User

sealed class ProfileFragmentViewEffect {
    object NavigateToLoginScreen : ProfileFragmentViewEffect()
    data class ProvideUserInfo(val user: User) : ProfileFragmentViewEffect()
}

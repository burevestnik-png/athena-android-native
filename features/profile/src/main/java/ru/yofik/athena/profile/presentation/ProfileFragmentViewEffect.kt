package ru.yofik.athena.profile.presentation

import ru.yofik.athena.common.domain.model.users.UserV2

sealed class ProfileFragmentViewEffect {
    object NavigateToLoginScreen : ProfileFragmentViewEffect()
    data class ProvideUserInfo(val user: UserV2) : ProfileFragmentViewEffect()
}

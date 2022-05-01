package ru.yofik.athena.settings.presentation

import ru.yofik.athena.common.domain.model.user.User

sealed class SettingsFragmentViewEffect {
    object NavigateToLoginScreen : SettingsFragmentViewEffect()
    data class ProvideUserInfo(val user: User) : SettingsFragmentViewEffect()
}

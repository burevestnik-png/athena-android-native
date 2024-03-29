package ru.yofik.athena.main.presentation

import ru.yofik.athena.common.domain.model.users.User

sealed class MainActivityViewEffect {
    data class SetStartDestination(val destination: Int) : MainActivityViewEffect()
    data class ProvideUserInfo(val user: User) : MainActivityViewEffect()
}

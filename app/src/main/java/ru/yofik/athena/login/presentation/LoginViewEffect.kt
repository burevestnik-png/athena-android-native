package ru.yofik.athena.login.presentation

sealed class LoginViewEffect {
    object NavigateToChatListPage : LoginViewEffect()
}

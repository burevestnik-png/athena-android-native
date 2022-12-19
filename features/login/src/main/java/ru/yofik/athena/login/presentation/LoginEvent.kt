package ru.yofik.athena.login.presentation

sealed class LoginEvent {
    object RequestUserActivation : LoginEvent()
    data class OnCodeValueChange(val value: String) : LoginEvent()
    data class OnUserIdValueChange(val value: String) : LoginEvent()
}

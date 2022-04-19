package ru.yofik.athena.settings.presentation

sealed class SettingsFragmentEvent {
    object LogoutUser : SettingsFragmentEvent()
}

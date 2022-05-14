package ru.yofik.athena.profile.presentation

sealed class ProfileFragmentEvent {
    object LogoutUser : ProfileFragmentEvent()
}

package ru.yofik.athena.settings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import ru.yofik.athena.settings.domain.usecases.LogoutUser

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(private val logoutUser: LogoutUser) :
    ViewModel() {
    private val _effects = MutableLiveData<SettingsFragmentViewEffect>()
    val effects: LiveData<SettingsFragmentViewEffect>
        get() = _effects

    fun onEvent(event: SettingsFragmentEvent) {
        when (event) {
            is SettingsFragmentEvent.LogoutUser -> handleLogoutUser()
        }
    }

    private fun handleLogoutUser() {
        logoutUser()
        _effects.value = SettingsFragmentViewEffect.NavigateToLoginScreen
    }
}

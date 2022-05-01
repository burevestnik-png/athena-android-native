package ru.yofik.athena.settings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.yofik.athena.settings.domain.usecases.GetCachedUser
import ru.yofik.athena.settings.domain.usecases.LogoutUser
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(
    private val logoutUser: LogoutUser, private val getCachedUser: GetCachedUser
) :
    ViewModel() {
    private val _effects = MutableLiveData<SettingsFragmentViewEffect>()
    val effects: LiveData<SettingsFragmentViewEffect>
        get() = _effects

    private val _state = MutableLiveData<SettingsViewState>(SettingsViewState())
    val state: LiveData<SettingsViewState>
        get() = _state

    init {
        provideUserInfo()
    }

    fun onEvent(event: SettingsFragmentEvent) {
        when (event) {
            is SettingsFragmentEvent.LogoutUser -> handleLogoutUser()
        }
    }

    private fun provideUserInfo() {
        _state.value = state.value!!.copy(loading = true)

        val user = getCachedUser()
        _effects.value = SettingsFragmentViewEffect.ProvideUserInfo(user)

        _state.value = state.value!!.copy(loading = false)
    }

    private fun handleLogoutUser() {
        logoutUser()
        _effects.value = SettingsFragmentViewEffect.NavigateToLoginScreen
    }
}

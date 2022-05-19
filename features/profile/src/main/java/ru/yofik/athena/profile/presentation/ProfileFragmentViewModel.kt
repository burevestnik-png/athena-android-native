package ru.yofik.athena.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import ru.yofik.athena.profile.domain.usecases.GetCachedUser
import ru.yofik.athena.profile.domain.usecases.LogoutUser

@HiltViewModel
class ProfileFragmentViewModel
@Inject
constructor(private val logoutUser: LogoutUser, private val getCachedUser: GetCachedUser) :
    ViewModel() {
    private val _effects = MutableLiveData<ProfileFragmentViewEffect>()
    val effects: LiveData<ProfileFragmentViewEffect>
        get() = _effects

    private val _state = MutableLiveData<ProfileViewState>()
    val state: LiveData<ProfileViewState>
        get() = _state

    init {
        _state.value = ProfileViewState()
        provideUserInfo()
    }

    fun onEvent(event: ProfileFragmentEvent) {
        when (event) {
            is ProfileFragmentEvent.LogoutUser -> handleLogoutUser()
        }
    }

    private fun provideUserInfo() {
        _state.value = state.value!!.copy(loading = true)

        val user = getCachedUser()
        _effects.value = ProfileFragmentViewEffect.ProvideUserInfo(user)

        _state.value = state.value!!.copy(loading = false)
    }

    private fun handleLogoutUser() {
        logoutUser()
        _effects.value = ProfileFragmentViewEffect.NavigateToLoginScreen
    }
}

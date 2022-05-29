package ru.yofik.athena.profile.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import ru.yofik.athena.common.presentation.components.base.BaseViewModel
import ru.yofik.athena.common.presentation.model.EmptyPayload
import ru.yofik.athena.profile.domain.usecases.GetCachedUser
import ru.yofik.athena.profile.domain.usecases.LogoutUser

@HiltViewModel
class ProfileFragmentViewModel
@Inject
constructor(private val logoutUser: LogoutUser, private val getCachedUser: GetCachedUser) :
    BaseViewModel<EmptyPayload>(EmptyPayload()) {
    private val _effects = MutableSharedFlow<ProfileFragmentViewEffect>()
    val effects: SharedFlow<ProfileFragmentViewEffect> = _effects

    ///////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////

    init {
        provideUserInfo()
    }

    ///////////////////////////////////////////////////////////////////////////
    // ON EVENT
    ///////////////////////////////////////////////////////////////////////////

    fun onEvent(event: ProfileFragmentEvent) {
        when (event) {
            is ProfileFragmentEvent.LogoutUser -> handleLogoutUser()
        }
    }

    private fun provideUserInfo() {
        showLoader()

        val user = getCachedUser()
        viewModelScope.launch { _effects.emit(ProfileFragmentViewEffect.ProvideUserInfo(user)) }

        hideLoader()
    }

    private fun handleLogoutUser() {
        launchIORequest {
            logoutUser()
            _effects.emit(ProfileFragmentViewEffect.NavigateToLoginScreen)
        }
    }

    override fun onFailure(throwable: Throwable) {}
}

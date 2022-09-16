package ru.yofik.athena.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.yofik.athena.R as ChatListR
import ru.yofik.athena.common.domain.usecases.GetCachedUser
import ru.yofik.athena.login.R as RLogin
import ru.yofik.athena.main.domain.usecases.HasAccess
import timber.log.Timber

@HiltViewModel
class MainActivityViewModel
@Inject
constructor(private val hasAccess: HasAccess, private val getCachedUser: GetCachedUser) :
    ViewModel() {

    private val _effects = MutableSharedFlow<MainActivityViewEffect>(replay = 2)
    val effects = _effects.asSharedFlow()

    ///////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////

    init {
        viewModelScope.launch {
            val user = getCachedUser()
            Timber.d(": $user")
            _effects.emit(MainActivityViewEffect.ProvideUserInfo(user))
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // ON EVENT
    ///////////////////////////////////////////////////////////////////////////

    fun onEvent(event: MainActivityEvent) {
        when (event) {
            is MainActivityEvent.DefineStartDestination -> defineStartDestination()
        }
    }

    private fun defineStartDestination() {
        val destination =
            if (hasAccess()) {
                ChatListR.id.nav_chat_list
            } else {
                RLogin.id.nav_login
            }

        viewModelScope.launch {
            _effects.emit(MainActivityViewEffect.SetStartDestination(destination))
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // ON FAILURE
    ///////////////////////////////////////////////////////////////////////////

    private fun onFailure(failure: Throwable) {
        // todo add
    }
}

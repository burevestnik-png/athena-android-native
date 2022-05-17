package ru.yofik.athena.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import ru.yofik.athena.R as ChatListR
import ru.yofik.athena.login.R as RLogin
import ru.yofik.athena.main.domain.usecases.HasAccess

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val hasAccess: HasAccess) : ViewModel() {
    private val _effects = MutableLiveData<MainActivityViewEffect>()

    val effects: LiveData<MainActivityViewEffect>
        get() = _effects

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

        _effects.value = MainActivityViewEffect.SetStartDestination(destination)
    }

    private fun onFailure(failure: Throwable) {
        // todo add
    }
}

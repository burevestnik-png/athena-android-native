package ru.yofik.athena.login.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext
import ru.yofik.athena.common.domain.model.exceptions.NetworkException
import ru.yofik.athena.common.domain.model.exceptions.NetworkUnavailableException
import ru.yofik.athena.common.presentation.components.base.BaseViewModel
import ru.yofik.athena.common.presentation.model.Event
import ru.yofik.athena.login.R
import ru.yofik.athena.login.domain.usecases.RequestUserActivation
import ru.yofik.athena.login.domain.usecases.RequestUserInfo
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel
@Inject
constructor(
    private val requestUserInfo: RequestUserInfo,
    private val requestUserActivation: RequestUserActivation,
) : BaseViewModel<LoginViewStatePayload>(LoginViewStatePayload()) {

    companion object {
        const val MAX_CODE_LENGTH = 3
    }

    private val _effects = MutableSharedFlow<LoginViewEffect>()
    val effects: SharedFlow<LoginViewEffect> = _effects

    ///////////////////////////////////////////////////////////////////////////
    // ON EVENT
    ///////////////////////////////////////////////////////////////////////////

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.RequestUserActivation -> onUserActivation()
            is LoginEvent.OnCodeValueChange -> onCodeValueChange(event.value)
            is LoginEvent.OnUserIdValueChange -> onUserIdValueChanged(event.value)
        }
    }

    private fun onUserIdValueChanged(newValue: String) {
        modifyState { payload -> payload.copy(userId = newValue.toLongOrNull() ?: 0) }
    }

    private fun onCodeValueChange(newValue: String) {
        val isCodeValid = newValue.length == MAX_CODE_LENGTH

        val codeError =
            if (isCodeValid || newValue.isEmpty()) {
                R.string.no_error
            } else {
                R.string.code_error
            }

        modifyState { payload -> payload.copy(code = newValue, codeError = codeError) }
    }

    private fun onUserActivation() {
        showLoader()

        launchIORequest {
            withContext(Dispatchers.IO) {
                requestUserActivation(payload.code, payload.userId)
                requestUserInfo()
            }

            hideLoader()
            _effects.emit(LoginViewEffect.NavigateToChatListPage)
        }
    }

    override fun onFailure(throwable: Throwable) {
        when (throwable) {
            // todo add to all handlers
            is NetworkUnavailableException,
            is NetworkException,
            -> {
                modifyState(loading = false, failure = Event(throwable))
            }
        }
    }
}

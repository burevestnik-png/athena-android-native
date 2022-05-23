package ru.yofik.athena.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.*
import ru.yofik.athena.common.domain.model.exceptions.NetworkException
import ru.yofik.athena.common.domain.model.exceptions.NetworkUnavailableException
import ru.yofik.athena.common.presentation.model.FailureEvent
import ru.yofik.athena.login.R
import ru.yofik.athena.login.domain.usecases.RequestUserActivation
import ru.yofik.athena.login.domain.usecases.RequestUserInfo
import timber.log.Timber

@HiltViewModel
class LoginFragmentViewModel
@Inject
constructor(
    private val requestUserActivation: RequestUserActivation,
    private val requestUserInfo: RequestUserInfo
) : ViewModel() {

    companion object {
        const val MAX_CODE_LENGTH = 3
    }

    private val _state = MutableLiveData<LoginViewState>()
    private val _effects = MutableLiveData<LoginViewEffect>()

    val effects: LiveData<LoginViewEffect>
        get() = _effects
    val state: LiveData<LoginViewState>
        get() = _state

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.d("exceptionHandler: ${throwable.message}")
        viewModelScope.launch { onFailure(throwable) }
    }

    init {
        _state.value = LoginViewState()
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.RequestUserActivation -> onUserActivation()
            is LoginEvent.OnCodeValueChange -> onCodeValueChange(event.value)
        }
    }

    private fun onCodeValueChange(newValue: String) {
        val isCodeValid = newValue.length == MAX_CODE_LENGTH

        val codeError =
            if (isCodeValid || newValue.isEmpty()) {
                R.string.no_error
            } else {
                R.string.code_error
            }

        _state.value = state.value!!.copy(code = newValue, codeError = codeError)
    }

    private fun onUserActivation() {
        _state.value = state.value!!.copy(loading = true)

        job =
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                requestUserActivation(state.value!!.code)
                requestUserInfo()

                withContext(Dispatchers.Main) {
                    _state.value = state.value!!.copy(loading = false)
                    _effects.value = LoginViewEffect.NavigateToChatListPage
                }
            }
    }

    private fun onFailure(failure: Throwable) {
        Timber.d("onFailure: $failure")
        when (failure) {
            is NetworkUnavailableException, is NetworkException -> {
                _state.value = state.value!!.copy(loading = false, failure = FailureEvent(failure))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

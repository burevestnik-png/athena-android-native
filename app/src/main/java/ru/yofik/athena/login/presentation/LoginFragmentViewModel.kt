package ru.yofik.athena.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.*
import ru.yofik.athena.common.domain.model.NetworkException
import ru.yofik.athena.common.domain.model.NetworkUnavailableException
import ru.yofik.athena.common.presentation.FailureEvent
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

    private val _state = MutableLiveData<LoginViewState>()
    val state: LiveData<LoginViewState>
        get() = _state

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.d("exceptionHandler: ${throwable.message}")
        viewModelScope.launch { onFailure(throwable) }
    }

    private val code = MutableLiveData("")

    init {
        _state.value = LoginViewState()
    }

    fun onEvent(event: LoginEvent) {
        Timber.d("onEvent: $event")
        when (event) {
            is LoginEvent.RequestUserActivation -> onUserActivation()
            is LoginEvent.OnCodeValueChange -> onCodeValueChange(event.value)
        }
    }

    private fun onCodeValueChange(newValue: String) {
        code.value = newValue
    }

    private fun onUserActivation() {
        setLoadingTrue()

        job =
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                requestUserActivation(code.value!!)
                requestUserInfo()

                withContext(Dispatchers.Main) {
                    _state.value =
                        state.value!!.copy(shouldNavigateToChatListScreen = true, loading = false)
                }
            }
    }

    private fun setLoadingTrue() {
        _state.value = state.value!!.copy(loading = true)
    }

    private fun onFailure(failure: Throwable) {
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

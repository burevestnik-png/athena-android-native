package ru.yofik.athena.createchat.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.*
import ru.yofik.athena.common.presentation.model.user.UiUserMapper
import ru.yofik.athena.createchat.domain.usecases.GetAllUsers
import timber.log.Timber

@HiltViewModel
class CreateChatFragmentViewModel
@Inject
constructor(private val getAllUsers: GetAllUsers, private val uiUserMapper: UiUserMapper) :
    ViewModel() {
    private var _state = MutableLiveData<CreateChatViewState>()
    val state: LiveData<CreateChatViewState>
        get() = _state

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.d("exceptionHandler: ${throwable.message}")
        viewModelScope.launch { onFailure(throwable) }
    }

    init {
        _state.value = CreateChatViewState()
    }

    fun onEvent(event: CreateChatEvent) {
        when (event) {
            is CreateChatEvent.GetAllUsers -> requestGetAllUsers()
        }
    }

    private fun requestGetAllUsers() {
        _state.value = state.value!!.copy(loading = true)
        job =
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val users = getAllUsers()
                Timber.d("requestGetAllUsers: ${users.joinToString("\n")}")

                withContext(Dispatchers.Main) {
                    _state.value =
                        state.value!!.copy(
                            loading = false,
                            users = users.map(uiUserMapper::mapToView)
                        )
                }
            }
    }

    private fun onFailure(throwable: Throwable) {}
}

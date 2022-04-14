package ru.yofik.athena.chatList.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ru.yofik.athena.login.domain.usecases.RequestUserInfo
import timber.log.Timber
import javax.inject.Inject

class ChatListFragmentViewModel @Inject constructor(
    private val requestUserInfo: RequestUserInfo
) : ViewModel() {
    private var _state = MutableLiveData<ChatListViewState>()
    val state: LiveData<ChatListViewState>
        get() = _state

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.d("exceptionHandler: ${throwable.message}")
        viewModelScope.launch { onFailure(throwable) }
    }

    init {
        _state.value = ChatListViewState()
    }

    fun onEvent(event: ChatListEvent) {
        when (event) {
            is ChatListEvent.GetAllChats -> getAllChats()
        }
    }

    private fun getAllChats() {}

    private fun setLoadingTrue() {
        _state.value = state.value!!.copy(loading = true)
    }

    private fun onFailure(throwable: Throwable) {

    }
}

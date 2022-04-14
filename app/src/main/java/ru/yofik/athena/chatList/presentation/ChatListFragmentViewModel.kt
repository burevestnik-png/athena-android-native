package ru.yofik.athena.chatList.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.*
import ru.yofik.athena.chatList.domain.usecases.GetAllChats
import timber.log.Timber

@HiltViewModel
class ChatListFragmentViewModel @Inject constructor(private val getAllChats: GetAllChats) :
    ViewModel() {
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
            is ChatListEvent.GetAllChats -> fetchAllChats()
        }
    }

    private fun fetchAllChats() {
        job =
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val res = getAllChats()
                Timber.d("getAllChats: ${res.joinToString("\n")}")
            }
    }

    private fun setLoadingTrue() {
        _state.value = state.value!!.copy(loading = true)
    }

    private fun onFailure(throwable: Throwable) {}
}

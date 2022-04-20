package ru.yofik.athena.chatlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.*
import ru.yofik.athena.chatlist.domain.model.mappers.UiChatMapper
import ru.yofik.athena.chatlist.domain.usecases.GetAllChats
import timber.log.Timber

@HiltViewModel
class ChatListFragmentViewModel @Inject constructor(private val getAllChats: GetAllChats,
private val uiChatMapper: UiChatMapper) :
    ViewModel() {
    private val _state = MutableLiveData<ChatListViewState>()
    val state: LiveData<ChatListViewState>
        get() = _state

    private val _effects = MutableLiveData<ChatListFragmentViewEffect>()
    val effect: LiveData<ChatListFragmentViewEffect>
        get() = _effects

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
            is ChatListEvent.GetAllChats -> requestAllChats()
        }
    }

    private fun requestAllChats() {
        setLoadingTrue()

        job =
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val chats = getAllChats()

                withContext(Dispatchers.Main) {
                    _state.value = state.value!!.copy(
                        loading = false,
                        chats = chats.map(uiChatMapper::mapToView)
                )
                }
            }
    }

    private fun setLoadingTrue() {
        _state.value = state.value!!.copy(loading = true)
    }

    private fun onFailure(throwable: Throwable) {}
}

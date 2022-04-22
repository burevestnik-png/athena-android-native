package ru.yofik.athena.chat.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class ChatFragmentViewModel : ViewModel() {
    private val _state = MutableLiveData(ChatFragmentState())
    val state: LiveData<ChatFragmentState>
        get() = _state

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.d("exceptionHandler: ${throwable.message}")
        viewModelScope.launch { onFailure(throwable) }
    }

    fun onEvent(event: ChatFragmentEvent) {
        when (event) {
            is ChatFragmentEvent.GetAllMessages -> handleGetAllMessages()
            is ChatFragmentEvent.SendMessage -> handleSendMessage(event.content)
            is ChatFragmentEvent.SetChatId -> handleSetChatId(event.id)
        }
    }

    private fun handleSetChatId(id: Long) {
        _state.value = state.value!!.copy(id = id)
    }

    private fun handleSendMessage(content: String) {}

    private fun handleGetAllMessages() {}

    private fun onFailure(throwable: Throwable) {
        // TODO
    }

    override fun onCleared() {
        super.onCleared()
        job = null
    }
}

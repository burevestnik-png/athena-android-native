package ru.yofik.athena.chat.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.*
import ru.yofik.athena.chat.domain.usecases.GetChat
import ru.yofik.athena.chat.domain.usecases.SendMessage
import timber.log.Timber

@HiltViewModel
class ChatFragmentViewModel
@Inject
constructor(private val getChat: GetChat, private val sendMessage: SendMessage) : ViewModel() {
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
            is ChatFragmentEvent.SendMessage -> handleSendMessage(event.content)
            is ChatFragmentEvent.GetChat -> handleGetChat(event.id)
        }
    }

    private fun handleGetChat(id: Long) {
        _state.value = state.value!!.copy(loading = true)
        job =
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val chat = getChat(id)
                withContext(Dispatchers.Main) {
                    _state.value = state.value!!.copy(loading = false, chatWithDetails = chat)
                }
            }
    }

    private fun handleSendMessage(content: String) {
        _state.value = state.value!!.copy(loading = true)
        job =
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                sendMessage(state.value!!.chatWithDetails.id, content)
                withContext(Dispatchers.Main) { _state.value = state.value!!.copy(loading = false) }
            }
    }

    private fun onFailure(throwable: Throwable) {
        // TODO
    }

    override fun onCleared() {
        super.onCleared()
        job = null
    }
}

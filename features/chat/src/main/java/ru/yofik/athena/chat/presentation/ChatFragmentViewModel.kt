package ru.yofik.athena.chat.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import kotlinx.coroutines.*
import ru.yofik.athena.chat.domain.usecases.GetChat
import ru.yofik.athena.chat.domain.usecases.SendMessage
import ru.yofik.athena.common.data.api.ws.RxNotificationEvent
import ru.yofik.athena.common.data.api.ws.RxNotificationPublisher
import timber.log.Timber

@HiltViewModel
class ChatFragmentViewModel
@Inject
constructor(
    private val getChat: GetChat,
    private val sendMessage: SendMessage,
) : ViewModel() {
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
            is ChatFragmentEvent.SendMessage -> handleSendMessage()
            is ChatFragmentEvent.GetChat -> handleGetChat(event.id)
            is ChatFragmentEvent.UpdateInput -> handleUpdateInput(event.content)
        }
    }

    private fun handleUpdateInput(value: String) {
        _state.value = state.value!!.copy(input = value)
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

    private fun handleSendMessage() {
        _state.value = state.value!!.copy(loading = true)
        job =
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                sendMessage(state.value!!.chatWithDetails.id, state.value!!.input)

                val messages = state.value!!.chatWithDetails.details.messages.toMutableList()

                //                messages.add(Message(
                //                    id = -1,
                //                    content = state.value!!.input,
                //                    senderId =
                //                ))

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

package ru.yofik.athena.chat.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import ru.yofik.athena.chat.domain.model.UiChat
import ru.yofik.athena.chat.domain.model.mappers.UiChatMapper
import ru.yofik.athena.chat.domain.model.mappers.UiMessageMapper
import ru.yofik.athena.chat.domain.usecases.GetChat
import ru.yofik.athena.chat.domain.usecases.GetUserId
import ru.yofik.athena.chat.domain.usecases.SendMessage
import ru.yofik.athena.chat.domain.usecases.SubscribeOnNewMessageNotifications
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import ru.yofik.athena.common.presentation.components.base.BaseViewModel
import ru.yofik.athena.common.presentation.model.UIState
import timber.log.Timber

@HiltViewModel
class ChatFragmentViewModel
@Inject
constructor(
    private val getChat: GetChat,
    private val sendMessage: SendMessage,
    private val subscribeOnNewMessageNotifications: SubscribeOnNewMessageNotifications,
    private val getUserId: GetUserId,
    private val uiMessageMapper: UiMessageMapper,
    private val uiChatMapper: UiChatMapper
) : BaseViewModel<ChatFragmentPayload>() {
    private val _state = MutableUIStateFlow(ChatFragmentPayload())
    private val _effects = MutableSharedFlow<ChatFragmentViewEffect>()

    val state: StateFlow<UIState<ChatFragmentPayload>> = _state
    val effects: SharedFlow<ChatFragmentViewEffect> = _effects

    private lateinit var uiChat: UiChat

    // todo inject
    private val compositeDisposable = CompositeDisposable()

    ///////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////

    init {
        getChatInfo()
        subscribeOnMessagesUpdates()
    }

    private fun getChatInfo() {}

    private fun subscribeOnMessagesUpdates() {}

    ///////////////////////////////////////////////////////////////////////////
    // ON EVENT
    ///////////////////////////////////////////////////////////////////////////

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
                //                val chat = getChat(id)
                //                Timber.d("handleGetChat: got chat from api $chat")

                withContext(Dispatchers.Main) {
                    //                    uiChat.value = uiChatMapper.mapToView(Pair(chat,
                    // getUserId()))
                    _state.value =
                        state.value!!.copy(
                            loading = false,
                            //                            messages = chat.messages.map {
                            //                                uiMessageMapper.mapToView(Pair(it,
                            // uiChat.value!!))
                            //                            }
                            )

                    _effects.value = ChatFragmentViewEffect.SetChatName(uiChat.value!!.name)
                }
            }

        subscribeOnNotificationChannel(id)
    }

    private fun subscribeOnNotificationChannel(chatId: Long) {
        subscribeOnNewMessageNotifications(chatId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { handleNotification(it) }
            .addTo(compositeDisposable)
    }

    private fun handleNotification(notification: NewMessageNotification) {
        Timber.d("New notification in chat feature")
        val messages = state.value!!.messages.toMutableList()

        _state.value =
            state.value!!.copy(
                messages =
                    messages.apply {
                        add(uiMessageMapper.mapToView(Pair(notification.message, uiChat.value!!)))
                    }
            )
    }

    private fun handleSendMessage() {
        _state.value = showLoader(state)

        launchApiRequest {
            withContext(Dispatchers.IO) {
                sendMessage(uiChat.id, state.payload.input)
            }

            _state.value = state.value.copy(loading = false) { copy() }
        }

            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                sendMessage(uiChat.value!!.id, state.value!!.input)
                withContext(Dispatchers.Main) {
                    _state.value = state.value!!.copy(loading = false, input = "")
                    _effects.value = ChatFragmentViewEffect.ClearInput
                }
            }
    }

    override fun onFailure(throwable: Throwable) {
        when (throwable) {}
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

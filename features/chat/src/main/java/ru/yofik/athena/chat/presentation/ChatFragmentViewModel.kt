package ru.yofik.athena.chat.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import ru.yofik.athena.chat.domain.model.UiChat
import ru.yofik.athena.chat.domain.model.mappers.UiChatMapper
import ru.yofik.athena.chat.domain.model.mappers.UiMessageMapper
import ru.yofik.athena.chat.domain.usecases.GetChat
import ru.yofik.athena.chat.domain.usecases.GetCurrentUserId
import ru.yofik.athena.chat.domain.usecases.SendMessage
import ru.yofik.athena.chat.domain.usecases.SubscribeOnNewMessageNotifications
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import ru.yofik.athena.common.presentation.components.base.BaseViewModel
import timber.log.Timber

@HiltViewModel
class ChatFragmentViewModel
@Inject
constructor(
    private val getChat: GetChat,
    private val sendMessage: SendMessage,
    private val subscribeOnNewMessageNotifications: SubscribeOnNewMessageNotifications,
    private val getCurrentUserId: GetCurrentUserId,
    private val uiMessageMapper: UiMessageMapper,
    private val uiChatMapper: UiChatMapper
) : BaseViewModel<ChatFragmentPayload>(ChatFragmentPayload()) {
    private val _effects = MutableSharedFlow<ChatFragmentViewEffect>()
    val effects: SharedFlow<ChatFragmentViewEffect> = _effects

    private lateinit var uiChat: UiChat

    // todo inject
    private val compositeDisposable = CompositeDisposable()

    ///////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////

    init {
        subscribeOnMessagesUpdates()
    }

    private fun subscribeOnMessagesUpdates() {}

    ///////////////////////////////////////////////////////////////////////////
    // ON EVENT
    ///////////////////////////////////////////////////////////////////////////

    fun onEvent(event: ChatFragmentEvent) {
        when (event) {
            is ChatFragmentEvent.SendMessage -> handleSendMessage()
            is ChatFragmentEvent.GetChatInfo -> handleGetChatInfo(event.id)
            is ChatFragmentEvent.UpdateInput -> handleUpdateInput(event.content)
        }
    }

    private fun handleUpdateInput(value: String) = modifyState { payload ->
        payload.copy(input = value)
    }

    private fun handleGetChatInfo(id: Long) {
        showLoader()

        launchIORequest {
            val chat = withContext(Dispatchers.IO) { getChat(id) }
            uiChat = uiChatMapper.mapToView(Pair(chat, getCurrentUserId()))

            hideLoader()

            _effects.emit(ChatFragmentViewEffect.SetChatName(uiChat.name))
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
        //        val messages = state.value!!.messages.toMutableList()

        // todo update cache

        //        _state.value =
        //            state.value!!.copy(
        //                messages =
        //                    messages.apply {
        //                        add(uiMessageMapper.mapToView(Pair(notification.message,
        // uiChat.value!!)))
        //                    }
        //            )
    }

    private fun handleSendMessage() {
        showLoader()

        launchIORequest {
            withContext(Dispatchers.IO) { sendMessage(uiChat.id, payload.input) }

            modifyState(loading = false) { payload -> payload.copy(input = "") }
            _effects.emit(ChatFragmentViewEffect.ClearInput)
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

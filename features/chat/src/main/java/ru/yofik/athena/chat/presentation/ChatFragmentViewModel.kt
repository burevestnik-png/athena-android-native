package ru.yofik.athena.chat.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.yofik.athena.chat.domain.model.UiChat
import ru.yofik.athena.chat.domain.model.mappers.UiChatMapper
import ru.yofik.athena.chat.domain.model.mappers.UiMessageMapper
import ru.yofik.athena.chat.domain.usecases.*
import ru.yofik.athena.common.domain.model.exceptions.NoMoreItemsException
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.presentation.components.base.BaseViewModel
import ru.yofik.athena.common.presentation.model.FailureEvent
import timber.log.Timber

@HiltViewModel
class ChatFragmentViewModel
@Inject
constructor(
    private val getChat: GetChat,
    private val sendMessage: SendMessage,
    private val subscribeOnNewMessageNotifications: SubscribeOnNewMessageNotifications,
    private val getCurrentUserId: GetCurrentUserId,
    private val getMessages: GetMessages,
    private val requestNextMessagesPage: RequestNextMessagesPage,
    private val handleNewMessage: HandleNewMessage,
    private val uiMessageMapper: UiMessageMapper,
    private val uiChatMapper: UiChatMapper
) : BaseViewModel<ChatFragmentPayload>(ChatFragmentPayload()) {

    companion object {
        const val UI_PAGE_SIZE = Pagination.DEFAULT_PAGE_SIZE

        private const val IS_LAST_PAGE_INITIAL = false
        private const val CURRENT_PAGE_INITIAL = 0
    }

    private val _effects = MutableSharedFlow<ChatFragmentViewEffect>()
    val effects: SharedFlow<ChatFragmentViewEffect> = _effects

    private lateinit var uiChat: UiChat

    // todo inject
    private val compositeDisposable = CompositeDisposable()
    private var currentPage = 0

    var isLastPage = false
        private set

    ///////////////////////////////////////////////////////////////////////////
    // ON EVENT
    ///////////////////////////////////////////////////////////////////////////

    fun onEvent(event: ChatFragmentEvent) {
        when (event) {
            is ChatFragmentEvent.SendMessage -> handleSendMessage()
            is ChatFragmentEvent.GetChatInfo -> handleGetChatInfo(event.id)
            is ChatFragmentEvent.UpdateInput -> handleUpdateInput(event.content)
            is ChatFragmentEvent.RequestNextMessagePage -> loadNextMessagePage()
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
        subscribeOnMessagesUpdates(id)
    }

    private fun subscribeOnMessagesUpdates(chatId: Long) {
        viewModelScope.launch {
            getMessages(chatId)
                .distinctUntilChanged()
                .onEach {
                    if (hasNoMessagesStoredButCanLoadMore(it)) {
                        loadNextMessagePage()
                    }
                }
                .filter { it.isNotEmpty() }
                .catch { onFailure(it) }
                .collect { onNewMessageList(it) }
        }
    }

    private fun hasNoMessagesStoredButCanLoadMore(messages: List<Message>): Boolean {
        return messages.isEmpty() && !state.value.payload.noMoreMessagesAvailable
    }

    private fun onNewMessageList(messages: List<Message>) {
        val messagesFromServer = messages.map { uiMessageMapper.mapToView(it to uiChat) }

        val currentMessages = state.value.payload.messages
        val newMessages = messagesFromServer.subtract(currentMessages.toSet())
        val updatedList = currentMessages + newMessages

        modifyState { payload -> payload.copy(messages = updatedList) }
    }

    private fun loadNextMessagePage() {
        showLoader()

        launchIORequest {
            val pagination =
                withContext(Dispatchers.IO) {
                    requestNextMessagesPage(chatId = uiChat.id, pageNumber = currentPage)
                }

            currentPage = pagination.currentPage
            hideLoader()
        }
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

        launchIORequest(Dispatchers.IO) { handleNewMessage(notification) }

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
        when (throwable) {
            is NoMoreItemsException -> {
                isLastPage = true
                modifyState(loading = false, failure = FailureEvent(throwable)) { payload ->
                    payload.copy(noMoreMessagesAvailable = true)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

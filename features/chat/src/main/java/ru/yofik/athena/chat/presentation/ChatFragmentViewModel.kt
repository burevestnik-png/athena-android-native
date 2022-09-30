package ru.yofik.athena.chat.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.yofik.athena.chat.domain.model.UiChat
import ru.yofik.athena.chat.domain.model.mappers.UiChatMapper
import ru.yofik.athena.chat.domain.model.mappers.UiMessageMapper
import ru.yofik.athena.chat.domain.usecases.*
import ru.yofik.athena.common.domain.model.exceptions.NoMoreItemsException
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import ru.yofik.athena.common.presentation.components.base.BaseViewModel
import ru.yofik.athena.common.presentation.model.Event
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
    private val uiChatMapper: UiChatMapper,
) : BaseViewModel<ChatFragmentPayload>(ChatFragmentPayload()) {

    companion object {
        const val UI_PAGE_SIZE = 20
    }

    private val _effects = MutableSharedFlow<ChatFragmentViewEffect>(replay = 1)
    val effects: SharedFlow<ChatFragmentViewEffect> = _effects

    private lateinit var uiChat: UiChat

    // todo inject
    private val compositeDisposable = CompositeDisposable()
    private var currentPage = 0

    var isLastPage = false
        private set

    ///////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    // ON EVENT
    ///////////////////////////////////////////////////////////////////////////

    fun onEvent(event: ChatFragmentEvent) =
        when (event) {
            is ChatFragmentEvent.SendMessage -> handleSendMessage()
            is ChatFragmentEvent.GetChatInfo -> handleGetChatInfo(event.id)
            is ChatFragmentEvent.UpdateInput -> handleUpdateInput(event.content)
            is ChatFragmentEvent.RequestNextMessagePage -> loadNextMessagePage()
        }

    private fun handleUpdateInput(value: String) = modifyState { payload ->
        payload.copy(input = value)
    }

    private fun handleGetChatInfo(id: Long) = withLoading {
        launchIORequest {
            val chat = getChat(id)
            uiChat = uiChatMapper.mapToView(Pair(chat, getCurrentUserId()))
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
                    Timber.d("subscribeOnMessagesUpdates: ${it.size}")
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
        return (messages.isEmpty() || messages.size == 1) &&
            !state.value.payload.noMoreMessagesAvailable
    }

    private fun onNewMessageList(messages: List<Message>) {
        val uiMessages = messages.map { uiMessageMapper.mapToView(it to uiChat) }

        val currentMessages = state.value.payload.messages
        val newMessages = uiMessages.subtract(currentMessages.toSet())

        val recentCurrentMessage = currentMessages.maxOfOrNull { it.dateTime }
        val recentNewMessage = newMessages.maxOf { it.dateTime }

        val updatedList =
            if (recentCurrentMessage != null) {
                if (recentNewMessage.isAfter(recentCurrentMessage)) {
                    currentMessages + newMessages.toList()
                } else {
                    newMessages.toList() + currentMessages
                }
            } else {
                newMessages.toList()
            }

        modifyState { payload -> payload.copy(messages = updatedList) }
    }

    private fun loadNextMessagePage() = withLoading {
        Timber.d("loadNextMessagePage: $currentPage")

        launchIORequest {
            val pagination =
                requestNextMessagesPage(
                    chatId = uiChat.id,
                    pageNumber = currentPage,
                    pageSize = UI_PAGE_SIZE
                )

            currentPage = pagination.currentPage
        }
    }

    private fun subscribeOnNotificationChannel(chatId: Long) {
        subscribeOnNewMessageNotifications(chatId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { handleNotification(it) }
            .addTo(compositeDisposable)
    }

    private fun handleNotification(notification: NewMessageNotification) = launchIORequest {
        handleNewMessage(notification)
    }

    private fun handleSendMessage() {
        showLoader()

        launchIORequest {
            sendMessage(uiChat.id, payload.input)
            modifyState(loading = false) { payload -> payload.copy(input = "") }
            _effects.emit(ChatFragmentViewEffect.ClearInput)
        }
    }

    override fun onFailure(throwable: Throwable) {
        when (throwable) {
            is NoMoreItemsException -> {
                isLastPage = true
                modifyState(loading = false, failure = Event(throwable)) { payload ->
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

package ru.yofik.athena.chatlist.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.yofik.athena.chatlist.domain.model.mappers.UiChatMapper
import ru.yofik.athena.chatlist.domain.model.mappers.UiMessageMapper
import ru.yofik.athena.chatlist.domain.usecases.*
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.model.exceptions.NoMoreItemsException
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.presentation.components.base.BaseViewModel
import ru.yofik.athena.common.presentation.model.FailureEvent
import ru.yofik.athena.common.presentation.model.UIState
import timber.log.Timber

@HiltViewModel
class ChatListFragmentViewModel
@Inject
constructor(
    private val getChats: GetChats,
    private val listenNewMessageNotifications: ListenNewMessageNotifications,
    private val requestNextChatsPage: RequestNextChatsPage,
    private val subscribeOnNotifications: SubscribeOnNotifications,
    private val forceRefreshChats: ForceRefreshChats,
    private val updateMessage: UpdateMessage,
    private val uiChatMapper: UiChatMapper,
    private val uiMessageMapper: UiMessageMapper
) : BaseViewModel<ChatListViewPayload>(ChatListViewPayload()) {

    companion object {
        const val UI_PAGE_SIZE = Pagination.DEFAULT_PAGE_SIZE

        private const val IS_LAST_PAGE_INITIAL = false
        private const val CURRENT_PAGE_INITIAL = 0
    }

    // todo provide like in BP
    private val compositeDisposable = CompositeDisposable()
    private var currentPage = 0

    var isLastPage = false
        private set

    ///////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////

    init {
        listenNotifications()
        subscribeOnChatsUpdates()
    }

    private fun subscribeOnChatsUpdates() {
        viewModelScope.launch {
            getChats()
                .distinctUntilChanged()
                .onEach {
                    Timber.d("subscribeOnChatsUpdates: onEach")
                    if (hasNoChatsStoredButCanLoadMore(it)) {
                        Timber.d("subscribeOnChatsUpdates: in has no chat")
                        loadNextChatPage()
                    }
                }
                .filter { it.isNotEmpty() }
                .catch { onFailure(it) }
                .collect { onNewChatList(it) }
        }
    }

    private fun onNewChatList(chats: List<Chat>) {
        val chatFromServer = chats.map(uiChatMapper::mapToView)

        val currentChats = state.value.payload.chats
        val newChats = chatFromServer.subtract(currentChats.toSet())
        val updatedList = currentChats + newChats

        modifyState { payload -> payload.copy(chats = updatedList) }
    }

    private fun loadNextChatPage() {
        showLoader()

        launchIORequest {
            val pagination = withContext(Dispatchers.IO) { requestNextChatsPage(currentPage) }
            currentPage = pagination.currentPage
            hideLoader()
        }
    }

    private fun hasNoChatsStoredButCanLoadMore(chats: List<Chat>): Boolean {
        return chats.isEmpty() && !state.value.payload.noMoreChatsAnymore
    }

    private fun listenNotifications() {
        subscribeOnNotifications()
        listenNewMessageNotifications()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { handleNewNotification(it) }
            .addTo(compositeDisposable)
    }

    private fun handleNewNotification(notification: NewMessageNotification) {
        Timber.d("Get new notification in chatList feature")

        // todo update cache
        // will list update if I only change db
        viewModelScope.launch(Dispatchers.IO) {
            updateMessage(notification.message)
        }
//        val updatedList =
//            state.value.payload.chats.map {
//                if (it.id == notification.message.chatId) {
//                    it.copy(message = uiMessageMapper.mapToView(notification.message))
//                } else {
//                    it
//                }
//            }

//        modifyState { payload -> payload.copy(chats = updatedList) }
    }

    ///////////////////////////////////////////////////////////////////////////
    // ON EVENT
    ///////////////////////////////////////////////////////////////////////////

    fun onEvent(event: ChatListEvent) {
        when (event) {
            is ChatListEvent.ForceGetAllChats -> handleForceGetAllChats()
            is ChatListEvent.RequestNextChatsPage -> loadNextChatPage()
        }
    }

    private fun handleForceGetAllChats() {
        showLoader()

        viewModelScope.launch {
            withContext(Dispatchers.IO) { forceRefreshChats() }

            isLastPage = IS_LAST_PAGE_INITIAL
            currentPage = CURRENT_PAGE_INITIAL

            _state.value = UIState(ChatListViewPayload())
        }
    }

    override fun onFailure(throwable: Throwable) {
        when (throwable) {
            is NoMoreItemsException -> {
                isLastPage = true
                modifyState(loading = false, failure = FailureEvent(throwable)) { payload ->
                    payload.copy(noMoreChatsAnymore = true)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

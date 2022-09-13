package ru.yofik.athena.chatlist.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.yofik.athena.chatlist.domain.model.mappers.UiChatMapper
import ru.yofik.athena.chatlist.domain.usecases.*
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.model.exceptions.NoMoreItemsException
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.presentation.components.base.BaseViewModel
import ru.yofik.athena.common.presentation.model.Event
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
    private val removeChatCache: RemoveChatCache,
    private val updateMessage: UpdateMessage,
    private val uiChatMapper: UiChatMapper,
) : BaseViewModel<ChatListFragmentPayload>(ChatListFragmentPayload()) {

    companion object {
        const val UI_PAGE_SIZE = Pagination.DEFAULT_PAGE_SIZE

        private const val IS_LAST_PAGE_INITIAL = false
        private const val CURRENT_PAGE_INITIAL = 0
    }

    // todo provide like in BP
    private val compositeDisposable = CompositeDisposable()
    private var currentPage = 0

    private var gettingCachedChatsJob: Job? = null

    var isLastPage = false
        private set

    private val selectedChatIds: MutableList<Long> = mutableListOf()

    ///////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////

    init {
        listenNotifications()
        subscribeOnChatsUpdates()
    }

    private fun subscribeOnChatsUpdates() {
        gettingCachedChatsJob?.cancel()
        gettingCachedChatsJob =
            viewModelScope.launch {
                getChats()
                    .distinctUntilChanged()
                    .onEach {
                        Timber.d("subscribeOnChatsUpdates: onEach ${it}")
                        if (hasNoChatsStoredButCanLoadMore(it)) {
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
        val chatFromServerIds = chatFromServer.map { it.id }

        val currentChats = state.value.payload.chats.filter { it.id !in chatFromServerIds}
        val updatedList = currentChats + chatFromServer

        modifyState { payload -> payload.copy(chats = updatedList) }
    }

    private fun loadNextChatPage() {
        Timber.d("loadNextChatPage")
        showLoader()

        launchIORequest {
            val pagination = requestNextChatsPage(currentPage)
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
        launchIORequest { updateMessage(notification.message) }

        // todo update cache
        // will list update if I only change db
    }

    ///////////////////////////////////////////////////////////////////////////
    // ON EVENT
    ///////////////////////////////////////////////////////////////////////////

    fun onEvent(event: ChatListEvent) {
        Timber.d("onEvent: $event")
        when (event) {
            is ChatListEvent.ForceGetAllChats -> handleForceGetAllChats()
            is ChatListEvent.RequestNextChatsPage -> loadNextChatPage()
            is ChatListEvent.AddChatToSelection -> handleAddChatToSelection(event.chatId)
            is ChatListEvent.CancelSelection -> handleSelectionCancellation()
        }
    }

    private fun handleSelectionCancellation() {
        selectedChatIds.clear()
        modifyState { payload -> payload.copy(mode = ChatListFragmentPayload.Mode.DEFAULT) }
    }

    private fun handleAddChatToSelection(id: Long) {
        if (selectedChatIds.contains(id)) {
            selectedChatIds.remove(id)

            if (
                payload.mode == ChatListFragmentPayload.Mode.SELECTION && selectedChatIds.isEmpty()
            ) {
                modifyState { payload ->
                    payload.copy(
                        mode = ChatListFragmentPayload.Mode.DEFAULT,
                        chats = payload.toggleChatSelection(id)
                    )
                }
            } else {
                modifyState { payload -> payload.copy(chats = payload.toggleChatSelection(id)) }
            }
        } else {
            selectedChatIds.add(id)

            if (payload.mode != ChatListFragmentPayload.Mode.SELECTION) {
                modifyState { payload ->
                    payload.copy(
                        mode = ChatListFragmentPayload.Mode.SELECTION,
                        chats = payload.toggleChatSelection(id)
                    )
                }
            } else {
                modifyState { payload -> payload.copy(chats = payload.toggleChatSelection(id)) }
            }
        }
    }

    private fun handleForceGetAllChats() {
        showLoader()

        launchIORequest {
            removeChatCache()

            isLastPage = IS_LAST_PAGE_INITIAL
            currentPage = CURRENT_PAGE_INITIAL
            _state.value = UIState(ChatListFragmentPayload())

            subscribeOnChatsUpdates()
        }
    }

    override fun onFailure(throwable: Throwable) {
        when (throwable) {
            is NoMoreItemsException -> {
                isLastPage = true
                modifyState(loading = false, failure = Event(throwable)) { payload ->
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

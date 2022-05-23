package ru.yofik.athena.chatlist.presentation

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
import kotlinx.coroutines.flow.*
import ru.yofik.athena.chatlist.domain.model.mappers.UiChatMapper
import ru.yofik.athena.chatlist.domain.model.mappers.UiMessageMapper
import ru.yofik.athena.chatlist.domain.usecases.GetChats
import ru.yofik.athena.chatlist.domain.usecases.ListenNewMessageNotifications
import ru.yofik.athena.chatlist.domain.usecases.RequestNextChatsPage
import ru.yofik.athena.chatlist.domain.usecases.SubscribeOnNotifications
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import timber.log.Timber

@HiltViewModel
class ChatListFragmentViewModel
@Inject
constructor(
    private val getChats: GetChats,
    private val listenNewMessageNotifications: ListenNewMessageNotifications,
    private val requestNextChatsPage: RequestNextChatsPage,
    private val uiChatMapper: UiChatMapper,
    private val uiMessageMapper: UiMessageMapper,
    subscribeOnNotifications: SubscribeOnNotifications
) : ViewModel() {
    private val _state = MutableStateFlow(ChatListViewState())
    val state: StateFlow<ChatListViewState> = _state

    private val compositeDisposable = CompositeDisposable()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.d("exceptionHandler: ${throwable.message}")
        viewModelScope.launch { onFailure(throwable) }
    }

    private var isLastPage = false
    private var currentPage = 0

    init {
        subscribeOnNotifications()
        listenNotifications()
        subscribeOnChatsUpdates()
    }

    private fun subscribeOnChatsUpdates() {
        viewModelScope.launch {
            getChats()
                .onEach {
                    Timber.d("subscribeOnChatsUpdates: onEach")
                    if (hasNoChatsStoredButCanLoadMore(it)) {
                        Timber.d("subscribeOnChatsUpdates: in has no chat")
                        loadNextChatPage()
                    }
                }
                .filter { it.isNotEmpty() }
                .catch {
                    Timber.d("subscribeOnChatsUpdates: exception ${it.message}")
                    onFailure(it)
                }
                .collect { onNewChatList(it) }
        }
    }

    private fun onNewChatList(chats: List<Chat>) {
        val chatFromServer = chats.map(uiChatMapper::mapToView)

        val currentChats = state.value.chats
        val newChats = chatFromServer.subtract(currentChats.toSet())
        val updatedList = currentChats + newChats

        _state.value = state.value.copy(chats = updatedList)
    }

    private fun loadNextChatPage() {
        _state.value = state.value.copy(loading = true)

        viewModelScope.launch(exceptionHandler) {
            val pagination = withContext(Dispatchers.IO) { requestNextChatsPage(++currentPage) }

            isLastPage = !pagination.canLoadMore
            currentPage = pagination.currentPage
            _state.value = state.value.copy(loading = false)
        }
    }

    private fun hasNoChatsStoredButCanLoadMore(chats: List<Chat>): Boolean {
        return chats.isEmpty() && !state.value.noMoreChatsAnymore
    }

    private fun listenNotifications() {
        listenNewMessageNotifications()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { handleNewNotification(it) }
            .addTo(compositeDisposable)
    }

    private fun handleNewNotification(notification: NewMessageNotification) {
        Timber.d("Get new notification in chatList feature")

        // todo update cache
        val updatedList =
            state.value.chats.map {
                if (it.id == notification.message.chatId) {
                    it.copy(message = uiMessageMapper.mapToView(notification.message))
                } else {
                    it
                }
            }

        _state.value = state.value.copy(chats = updatedList)
    }

    fun onEvent(event: ChatListEvent) {
        when (event) {
            is ChatListEvent.ForceGetAllChats -> handleForceGetAllChats()
        }
    }

    private fun handleForceGetAllChats() {

    }

    private fun onFailure(throwable: Throwable) {}

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

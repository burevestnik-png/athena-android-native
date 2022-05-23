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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
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
    private val subscribeOnNotifications: SubscribeOnNotifications,
    private val requestNextChatsPage: RequestNextChatsPage,
    private val uiChatMapper: UiChatMapper,
    private val uiMessageMapper: UiMessageMapper
) : ViewModel() {
    private val _state = MutableLiveData<ChatListViewState>()
    val state: LiveData<ChatListViewState>
        get() = _state

    private val compositeDisposable = CompositeDisposable()

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.d("exceptionHandler: ${throwable.message}")
        viewModelScope.launch { onFailure(throwable) }
    }

    private var isLastPage = false
    private var currentPage = 0

    init {
        _state.value = ChatListViewState()

        subscribeOnNotifications()
        listenNotifications()

        subscribeOnChatsUpdates()
    }

    private fun subscribeOnChatsUpdates() {
        viewModelScope.launch {
            getChats()
                .onEach {
                    if (hasNoAnimalsStoredButCanLoadMore(it)) {
                        withContext(Dispatchers.IO) { loadNextChatPage() }
                    }
                }
                .catch { Timber.d("subscribeOnChatsUpdates: exception") }
                .collect { onNewChatList(it) }
        }
    }

    private fun onNewChatList(chats: List<Chat>) {
        _state.value = state.value!!.copy(chats = chats.map(uiChatMapper::mapToView))
    }

    private fun loadNextChatPage() {

    }

    private fun hasNoAnimalsStoredButCanLoadMore(chats: List<Chat>): Boolean {
        return chats.isEmpty() && !state.value!!.noMoreChatsAnymore
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
            state.value!!.chats.map {
                if (it.id == notification.message.chatId) {
                    it.copy(message = uiMessageMapper.mapToView(notification.message))
                } else {
                    it
                }
            }

        _state.value = state.value!!.copy(chats = updatedList)
    }

    fun onEvent(event: ChatListEvent) {
        when (event) {}
    }

    private fun requestAllChats() {
        setLoadingTrue()

        job =
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
//                val chats = getAllChats()

            }
    }

    private fun setLoadingTrue() {
        _state.value = state.value!!.copy(loading = true)
    }

    private fun onFailure(throwable: Throwable) {}

    override fun onCleared() {
        super.onCleared()
        job = null
        compositeDisposable.clear()
    }
}

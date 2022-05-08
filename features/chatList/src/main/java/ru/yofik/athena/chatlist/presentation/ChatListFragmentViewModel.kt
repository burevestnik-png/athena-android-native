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
import ru.yofik.athena.chatlist.domain.model.mappers.UiChatMapper
import ru.yofik.athena.chatlist.domain.model.mappers.UiMessageMapper
import ru.yofik.athena.chatlist.domain.usecases.GetAllChats
import ru.yofik.athena.chatlist.domain.usecases.SubscribeOnNewMessageNotifications
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import timber.log.Timber

@HiltViewModel
class ChatListFragmentViewModel
@Inject
constructor(
    private val getAllChats: GetAllChats,
    private val subscribeOnNotifications: SubscribeOnNewMessageNotifications,
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

    init {
        _state.value = ChatListViewState()
        subscribeOnNotificationChannel()
    }

    fun onEvent(event: ChatListEvent) {
        when (event) {
            is ChatListEvent.GetAllChats -> requestAllChats()
        }
    }

    private fun subscribeOnNotificationChannel() {
        subscribeOnNotifications()
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

    private fun requestAllChats() {
        setLoadingTrue()

        job =
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val chats = getAllChats()

                withContext(Dispatchers.Main) {
                    _state.value =
                        state.value!!.copy(
                            loading = false,
                            chats = chats.map(uiChatMapper::mapToView)
                        )
                }
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

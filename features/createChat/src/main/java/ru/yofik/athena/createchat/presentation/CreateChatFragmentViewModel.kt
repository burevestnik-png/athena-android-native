package ru.yofik.athena.createchat.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.yofik.athena.common.domain.model.exceptions.NoMoreItemsException
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.model.user.User
import ru.yofik.athena.common.presentation.components.base.BaseViewModel
import ru.yofik.athena.common.presentation.model.FailureEvent
import ru.yofik.athena.common.presentation.model.UIState
import ru.yofik.athena.createchat.domain.model.UiUserMapper
import ru.yofik.athena.createchat.domain.model.exceptions.ChatAlreadyCreatedException
import ru.yofik.athena.createchat.domain.usecases.CreateChat
import ru.yofik.athena.createchat.domain.usecases.ForceRefreshUsers
import ru.yofik.athena.createchat.domain.usecases.GetUsers
import ru.yofik.athena.createchat.domain.usecases.RequestNextUsersPage
import timber.log.Timber

@HiltViewModel
class CreateChatFragmentViewModel
@Inject
constructor(
    private val getUsers: GetUsers,
    private val createChat: CreateChat,
    private val requestNextUsersPage: RequestNextUsersPage,
    private val forceRefreshUsers: ForceRefreshUsers,
    private val uiUserMapper: UiUserMapper
) : BaseViewModel<CreateChatStatePayload>(CreateChatStatePayload()) {

    companion object {
        const val UI_PAGE_SIZE = Pagination.DEFAULT_PAGE_SIZE

        private const val IS_LAST_PAGE_INITIAL = false
        private const val CURRENT_PAGE_INITIAL = 0
    }

    private var _effects = MutableSharedFlow<CreateChatFragmentViewEffect>()
    private var currentPage = 0

    val effects: SharedFlow<CreateChatFragmentViewEffect> = _effects
    var isLastPage = IS_LAST_PAGE_INITIAL
        private set

    ///////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////

    init {
        subscribeOnUsersUpdates()
    }

    private fun subscribeOnUsersUpdates() {
        viewModelScope.launch {
            getUsers()
                .distinctUntilChanged()
                .onEach {
                    Timber.d("OnEach size=${it.size}")
                    if (hasNoUsersStoredButCanLoadMore(it)) {
                        loadNextUserPage()
                    } else {
                        val amount = it.size
                        currentPage = amount / UI_PAGE_SIZE
                    }
                }
                .filter { it.isNotEmpty() }
                .catch { onFailure(it) }
                .collect { onNewUsersList(it) }
        }
    }

    private fun hasNoUsersStoredButCanLoadMore(users: List<User>): Boolean {
        return users.isEmpty() && !state.value.payload.noMoreUsersAnymore
    }

    private fun onNewUsersList(users: List<User>) {
        val userFromServer = users.map(uiUserMapper::mapToView)

        val currentUsers = state.value.payload.users
        val newUsers = userFromServer.subtract(currentUsers.toSet())
        val updatedList = currentUsers + newUsers

        _state.value = state.value.copy { copy(users = updatedList) }
        modifyState  }
    }

    ///////////////////////////////////////////////////////////////////////////
    // ON EVENT
    ///////////////////////////////////////////////////////////////////////////

    fun onEvent(event: CreateChatEvent) {
        when (event) {
            is CreateChatEvent.CreateChat -> handleCreateChat(event.targetUserId)
            is CreateChatEvent.RequestMoreUsers -> loadNextUserPage()
            is CreateChatEvent.ForceRequestAllUsers -> forceRequestAllUsers()
        }
    }

    private fun handleCreateChat(targetUserId: Long) {
        _state.value = showLoader(state)

        launchApiRequest {
            val createdChat = withContext(Dispatchers.IO) { createChat(targetUserId) }
            Timber.d("handleCreateChat: $createdChat")
//            _effects.emit(CreateChatFragmentViewEffect.NavigateToChatListScreen)

            _state.value = hideLoader(state)
        }
    }

    private fun loadNextUserPage() {
        _state.value = showLoader(state)

        launchApiRequest {
            val pagination = withContext(Dispatchers.IO) { requestNextUsersPage(currentPage) }
            currentPage = pagination.currentPage
            _state.value = hideLoader(state)
        }
    }

    private fun forceRequestAllUsers() {
        _state.value = showLoader(state)

        viewModelScope.launch {
            withContext(Dispatchers.IO) { forceRefreshUsers() }

            isLastPage = IS_LAST_PAGE_INITIAL
            currentPage = CURRENT_PAGE_INITIAL

            _state.value = UIState(CreateChatStatePayload())
        }
    }

    override fun onFailure(throwable: Throwable) {
        when (throwable) {
            is ChatAlreadyCreatedException -> {
                _state.value = state.value.copy(loading = false, failure = FailureEvent(throwable))
            }
            is NoMoreItemsException -> {
                isLastPage = true
                _state.value =
                    state.value.copy(loading = false, failure = FailureEvent(throwable)) {
                        copy(noMoreUsersAnymore = true)
                    }
            }
        }
    }
}

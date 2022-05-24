package ru.yofik.athena.createchat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.yofik.athena.common.domain.model.exceptions.NoMoreItemsException
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.model.user.User
import ru.yofik.athena.common.presentation.model.FailureEvent
import ru.yofik.athena.createchat.domain.model.UiUserMapper
import ru.yofik.athena.createchat.domain.model.exceptions.ChatAlreadyCreatedException
import ru.yofik.athena.createchat.domain.usecases.CreateChat
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
    private val uiUserMapper: UiUserMapper
) : ViewModel() {

    companion object {
        const val UI_PAGE_SIZE = Pagination.DEFAULT_PAGE_SIZE
    }

    private var _state = MutableStateFlow(CreateChatViewState())
    val state: StateFlow<CreateChatViewState> = _state

    private var _effects = MutableSharedFlow<CreateChatFragmentViewEffect>()
    val effects: SharedFlow<CreateChatFragmentViewEffect> = _effects

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.d("exceptionHandler: ${throwable.message}")
        onFailure(throwable)
    }

    var isLastPage = false
        private set

    private var currentPage = 0

    init {
        subscribeOnUsersUpdates()
    }

    private fun subscribeOnUsersUpdates() {
        viewModelScope.launch {
            getUsers()
                .distinctUntilChanged()
                .onEach {
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
        return users.isEmpty() && !state.value.noMoreUsersAnymore
    }



    private fun onNewUsersList(users: List<User>) {
        val userFromServer = users.map(uiUserMapper::mapToView)

        val currentUsers = state.value.users
        val newUsers = userFromServer.subtract(currentUsers.toSet())
        val updatedList = currentUsers + newUsers

        _state.value = state.value.copy(users = updatedList)
    }

    fun onEvent(event: CreateChatEvent) {
        when (event) {
            is CreateChatEvent.CreateChat -> handleCreateChat(event.id, event.name)
            is CreateChatEvent.RequestMoreUsers -> loadNextUserPage()
            is CreateChatEvent.ForceRequestAllUsers ->
        }
    }

    private fun handleCreateChat(id: Long, name: String) {
        _state.value = state.value.copy(loading = true)
        //        job =
        //            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
        //                val createdChat = createChat(name, id)
        //                Timber.d("handleCreateChat: $createdChat")
        //
        //                withContext(Dispatchers.Main) {
        //                    _effects.value = CreateChatFragmentViewEffect.NavigateToChatListScreen
        //                }
        //            }
    }

    private fun loadNextUserPage() {
        _state.value = state.value.copy(loading = true)

        viewModelScope.launch(exceptionHandler) {
            val pagination = withContext(Dispatchers.IO) { requestNextUsersPage(currentPage) }

            currentPage = pagination.currentPage
            _state.value = state.value.copy(loading = false)
        }
    }

    private fun forceRequestAllUsers() {
        
    }

    private fun onFailure(throwable: Throwable) {
        when (throwable) {
            is ChatAlreadyCreatedException -> {
                _state.value = state.value.copy(loading = false, failure = FailureEvent(throwable))
            }
            is NoMoreItemsException -> {
                isLastPage = true
                _state.value =
                    state.value.copy(
                        loading = false,
                        failure = FailureEvent(throwable),
                        noMoreUsersAnymore = true
                    )
            }
        }
    }
}

package ru.yofik.athena.createchat.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.*
import ru.yofik.athena.createchat.domain.model.UiUserMapper
import ru.yofik.athena.createchat.domain.usecases.CreateChat
import ru.yofik.athena.createchat.domain.usecases.GetAllUsers
import timber.log.Timber

@HiltViewModel
class CreateChatFragmentViewModel
@Inject
constructor(
    private val getAllUsers: GetAllUsers,
    private val createChat: CreateChat,
    private val uiUserMapper: UiUserMapper
) : ViewModel() {
    private var _state = MutableLiveData<CreateChatViewState>()
    val state: LiveData<CreateChatViewState>
        get() = _state

    private var _effects = MutableLiveData<CreateChatFragmentViewEffect>()
    val effects: LiveData<CreateChatFragmentViewEffect>
        get() = _effects

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.d("exceptionHandler: ${throwable.message}")
        viewModelScope.launch { onFailure(throwable) }
    }

    init {
        _state.value = CreateChatViewState()
    }

    fun onEvent(event: CreateChatEvent) {
        when (event) {
            is CreateChatEvent.GetAllUsers -> handleGetAllUsers()
            is CreateChatEvent.CreateChat -> handleCreateChat(event.id, event.name)
        }
    }

    private fun handleCreateChat(id: Long, name: String) {
        _state.value = state.value!!.copy(loading = true)
        job =
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val createdChat = createChat(name, id)
                Timber.d("handleCreateChat: $createdChat")

                withContext(Dispatchers.Main) {
                    _effects.value = CreateChatFragmentViewEffect.NavigateToChatListScreen
                }
            }
    }

    private fun handleGetAllUsers() {
        _state.value = state.value!!.copy(loading = true)
        job =
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val users = getAllUsers()
                Timber.d("requestGetAllUsers: ${users.joinToString("\n")}")

                withContext(Dispatchers.Main) {
                    _state.value =
                        state.value!!.copy(
                            loading = false,
                            users = users.map(uiUserMapper::mapToView)
                        )
                }
            }
    }

    private fun onFailure(throwable: Throwable) {
        // todo add
    }
}

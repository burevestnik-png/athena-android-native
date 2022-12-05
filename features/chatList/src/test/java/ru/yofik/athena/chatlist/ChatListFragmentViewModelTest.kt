package ru.yofik.athena.chatlist

import com.google.common.truth.Truth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import ru.yofik.athena.chatlist.domain.model.mappers.UiChatMapper
import ru.yofik.athena.chatlist.domain.model.mappers.UiMessageMapper
import ru.yofik.athena.chatlist.domain.usecases.*
import ru.yofik.athena.chatlist.presentation.ChatListEvent
import ru.yofik.athena.chatlist.presentation.ChatListFragmentPayload
import ru.yofik.athena.chatlist.presentation.ChatListFragmentViewModel
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.model.chat.ChatType
import ru.yofik.athena.common.domain.model.pagination.PaginatedChats
import ru.yofik.athena.common.domain.model.pagination.PaginatedUsers
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.model.users.User
import ru.yofik.athena.common.domain.repositories.ChatRepository
import ru.yofik.athena.common.domain.repositories.NotificationRepository
import ru.yofik.athena.common.presentation.model.UIState
import ru.yofik.athena.common.utils.DispatchersProvider
import ru.yofik.common.TestCoroutineRule

@ExperimentalCoroutinesApi
class ChatListFragmentViewModelTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var chatRepository: ChatRepository
    private lateinit var viewModel: ChatListFragmentViewModel
    private lateinit var notificationRepository: NotificationRepository

    private val uiChatMapper = UiChatMapper(UiMessageMapper())
    private val dispatchersProvider = object : DispatchersProvider {
        override fun io(): CoroutineDispatcher = testCoroutineRule.testDispatcher
    }

    @Before
    fun setup() {
        chatRepository = Mockito.mock(ChatRepository::class.java)
        notificationRepository = Mockito.mock(NotificationRepository::class.java)
    }

//    @Test
    fun `RequestNextChatPage page and there are more pages`() = runTest {
        // Given
        val user = User(1, "name", "login")
        val chats = listOf(
            Chat.empty(1, ChatType.PERSONAL, "name", listOf(user)),
            Chat.empty(1, ChatType.PERSONAL, "name", listOf(user)),
            Chat.empty(1, ChatType.PERSONAL, "name", listOf(user)),
            Chat.empty(1, ChatType.PERSONAL, "name", listOf(user)),
            Chat.empty(1, ChatType.PERSONAL, "name", listOf(user)),
            Chat.empty(1, ChatType.PERSONAL, "name", listOf(user)),
            Chat.empty(1, ChatType.PERSONAL, "name", listOf(user)),
            Chat.empty(1, ChatType.PERSONAL, "name", listOf(user)),
            Chat.empty(1, ChatType.PERSONAL, "name", listOf(user)),
            Chat.empty(1, ChatType.PERSONAL, "name", listOf(user)),
        )

        Mockito.`when`(chatRepository.getCachedChats()).thenReturn(flow {
            emit(chats)
        })

        Mockito.`when`(chatRepository.requestGetPaginatedChats(0, 10)).thenReturn(
            PaginatedChats(
                chats = chats,
                pagination = Pagination(
                    0,
                    chats.size
                )
            )
        )

        viewModel = ChatListFragmentViewModel(
            getChats = GetChats(chatRepository),
            uiChatMapper = uiChatMapper,
            updateMessage = UpdateMessage(chatRepository),
            removeChatCache = RemoveChatCache(chatRepository),
            dispatchersProvider = dispatchersProvider,
            requestNextChatsPage = RequestNextChatsPage(chatRepository),
            subscribeOnNotifications = SubscribeOnNotifications(notificationRepository),
            listenNewMessageNotifications = ListenNewMessageNotifications(notificationRepository)
        )

        val expectedState = UIState(
            payload = ChatListFragmentPayload(
                mode = ChatListFragmentPayload.Mode.DEFAULT,
                noMoreChatsAnymore = false,
                chats = emptyList()
            ),
            loading = false,
            failure = null
        )

        // When
        viewModel.onEvent(ChatListEvent.RequestNextChatsPage)

        // Then
        val viewState = viewModel.state.value
        Truth.assertThat(viewState).isEqualTo(expectedState)
    }
}
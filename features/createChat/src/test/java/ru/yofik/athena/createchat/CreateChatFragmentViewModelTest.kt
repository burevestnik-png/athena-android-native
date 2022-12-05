package ru.yofik.athena.createchat

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import ru.yofik.athena.common.domain.model.exceptions.NoMoreItemsException
import ru.yofik.athena.common.domain.model.pagination.PaginatedUsers
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.model.users.User
import ru.yofik.athena.common.domain.repositories.ChatRepository
import ru.yofik.athena.common.domain.repositories.UserProfileRepository
import ru.yofik.athena.common.domain.repositories.UserRepository
import ru.yofik.athena.common.presentation.model.Event
import ru.yofik.athena.common.presentation.model.UIState
import ru.yofik.athena.common.utils.DispatchersProvider
import ru.yofik.athena.createchat.domain.model.UiUserMapper
import ru.yofik.athena.createchat.domain.usecases.CreateChat
import ru.yofik.athena.createchat.domain.usecases.ForceRefreshUsers
import ru.yofik.athena.createchat.domain.usecases.GetUsers
import ru.yofik.athena.createchat.domain.usecases.RequestNextUsersPage
import ru.yofik.athena.createchat.presentation.CreateChatEvent
import ru.yofik.athena.createchat.presentation.CreateChatFragmentViewModel
import ru.yofik.athena.createchat.presentation.CreateChatStatePayload
import ru.yofik.common.TestCoroutineRule

@ExperimentalCoroutinesApi
class CreateChatFragmentViewModelTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val uiUserMapper = UiUserMapper()

    private lateinit var userRepository: UserRepository
    private lateinit var chatRepository: ChatRepository
    private lateinit var viewModel: CreateChatFragmentViewModel
    private lateinit var userProfileRepository: UserProfileRepository

    private val dispatchersProvider = object : DispatchersProvider {
        override fun io(): CoroutineDispatcher = testCoroutineRule.testDispatcher
    }

    @Before
    fun setup() {
        userRepository = Mockito.mock(UserRepository::class.java)
        chatRepository = Mockito.mock(ChatRepository::class.java)
        userProfileRepository = Mockito.mock(UserProfileRepository::class.java)
    }

    @Test
    fun `CreateChat completes successfully`() = runTest {
        // Given
        viewModel = CreateChatFragmentViewModel(
            getUsers = GetUsers(userProfileRepository, userRepository),
            createChat = CreateChat(chatRepository),
            forceRefreshUsers = ForceRefreshUsers(userProfileRepository),
            uiUserMapper = uiUserMapper,
            dispatchersProvider = dispatchersProvider,
            requestNextUsersPage = RequestNextUsersPage(userProfileRepository)
        )

        val expectedState = UIState(
            payload = CreateChatStatePayload(
                noMoreUsersAnymore = false,
                users = emptyList()
            ),
            loading = false,
            failure = null
        )

        // When
        viewModel.onEvent(CreateChatEvent.CreateChat(2))

        // Then
        val viewState = viewModel.state.value
        assertThat(viewState).isEqualTo(expectedState)
    }

    @Test
    fun `RequestNextUsersPage page and there are more pages`() = runTest {
        // Given
        val users = listOf(
            User(1, "name", "login"),
            User(1, "name", "login"),
            User(1, "name", "login"),
            User(1, "name", "login"),
            User(1, "name", "login"),
            User(1, "name", "login"),
            User(1, "name", "login"),
            User(1, "name", "login"),
            User(1, "name", "login"),
            User(1, "name", "login"),
        )

        `when`(userProfileRepository.getCachedUsers()).thenReturn(flow {
            emit(users)
        })

        `when`(userProfileRepository.requestGetPaginatedUsersProfiles(0, 10)).thenReturn(
            PaginatedUsers(
                users = users,
                pagination = Pagination(
                    0,
                    users.size
                )
            )
        )

        viewModel = CreateChatFragmentViewModel(
            getUsers = GetUsers(userProfileRepository, userRepository),
            createChat = CreateChat(chatRepository),
            forceRefreshUsers = ForceRefreshUsers(userProfileRepository),
            uiUserMapper = uiUserMapper,
            dispatchersProvider = dispatchersProvider,
            requestNextUsersPage = RequestNextUsersPage(userProfileRepository)
        )

        val expectedState = UIState(
            payload = CreateChatStatePayload(
                noMoreUsersAnymore = false,
                users = emptyList()
            ),
            loading = false,
            failure = null
        )

        // When
        viewModel.onEvent(CreateChatEvent.RequestMoreUsers)

        // Then
        val viewState = viewModel.state.value
        assertThat(viewState).isEqualTo(expectedState)
    }

    @Test
    fun `RequestNextUsersPage page and there are no more pages`() = runTest {
        // Given
        val users = listOf(
            User(1, "name", "login"),
            User(1, "name", "login"),
        )

        val exception = NoMoreItemsException()

        `when`(userProfileRepository.getCachedUsers()).thenReturn(flow {
            emit(users)
        })

        `when`(userProfileRepository.requestGetPaginatedUsersProfiles(0, 10)).thenThrow(exception)

        viewModel = CreateChatFragmentViewModel(
            getUsers = GetUsers(userProfileRepository, userRepository),
            createChat = CreateChat(chatRepository),
            forceRefreshUsers = ForceRefreshUsers(userProfileRepository),
            uiUserMapper = uiUserMapper,
            dispatchersProvider = dispatchersProvider,
            requestNextUsersPage = RequestNextUsersPage(userProfileRepository)
        )

        val expectedState = UIState(
            payload = CreateChatStatePayload(
                noMoreUsersAnymore = true,
                users = emptyList()
            ),
            loading = false,
            failure = Event(exception)
        )

        // When
        viewModel.onEvent(CreateChatEvent.RequestMoreUsers)

        // Then
        val viewState = viewModel.state.value
        assertThat(viewState).isEqualTo(expectedState)
    }
}
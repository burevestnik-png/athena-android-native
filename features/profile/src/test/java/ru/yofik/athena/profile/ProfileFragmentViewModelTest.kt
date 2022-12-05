package ru.yofik.athena.profile

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import ru.yofik.athena.common.domain.model.users.User
import ru.yofik.athena.common.domain.repositories.CommonRepository
import ru.yofik.athena.common.domain.repositories.UserRepository
import ru.yofik.athena.common.domain.usecases.GetCachedUser
import ru.yofik.athena.common.presentation.model.EmptyPayload
import ru.yofik.athena.common.presentation.model.UIState
import ru.yofik.athena.common.utils.DispatchersProvider
import ru.yofik.athena.profile.domain.usecases.LogoutUser
import ru.yofik.athena.profile.presentation.ProfileFragmentEvent
import ru.yofik.athena.profile.presentation.ProfileFragmentViewModel
import ru.yofik.common.TestCoroutineRule

@ExperimentalCoroutinesApi
class ProfileFragmentViewModelTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var userRepository: UserRepository
    private lateinit var commonRepository: CommonRepository
    private lateinit var viewModel: ProfileFragmentViewModel

    private val dispatchersProvider = object : DispatchersProvider {
        override fun io(): CoroutineDispatcher = testCoroutineRule.testDispatcher
    }

    @Before
    fun setup() {
        commonRepository = Mockito.mock(CommonRepository::class.java)
        userRepository = Mockito.mock(UserRepository::class.java)
    }

    @Test
    fun `LogoutUser completes successfully`() = runTest {
        // Given
        `when`(userRepository.getCachedUser()).thenReturn(User(1, "name", "login"))
        val payload = EmptyPayload()

        viewModel = ProfileFragmentViewModel(
            initialPayload = payload,
            logoutUser = LogoutUser(commonRepository),
            getCachedUser = GetCachedUser(userRepository),
            dispatchersProvider = dispatchersProvider
        )

        val expectedViewState = UIState(
            payload = payload,
            loading = false,
            failure = null
        )

        // When
        viewModel.onEvent(ProfileFragmentEvent.LogoutUser)

        // Then
        val viewState = viewModel.state.value
        assertThat(viewState).isEqualTo(expectedViewState)
    }
}
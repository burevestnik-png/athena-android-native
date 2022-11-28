package ru.yofik.athena.login

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.mockito.stubbing.Answer
import ru.yofik.athena.common.domain.repositories.UserRepository
import ru.yofik.athena.common.utils.DispatchersProvider
import ru.yofik.athena.login.domain.usecases.RequestUserActivation
import ru.yofik.athena.login.domain.usecases.RequestUserInfo
import ru.yofik.athena.login.presentation.LoginFragmentViewModel
import ru.yofik.common.TestCoroutineRule

@ExperimentalCoroutinesApi
class LoginFragmentViewModelTests {
    // Given
    // When
    // Then

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var repository: UserRepository
    private lateinit var viewModel: LoginFragmentViewModel

    private lateinit var requestUserInfo: RequestUserInfo
    private lateinit var requestUserActivation: RequestUserActivation

    private val dispatchersProvider = object : DispatchersProvider {
        override fun io(): CoroutineDispatcher = testCoroutineRule.testDispatcher
    }

    @Before
    fun setup() {
        repository = Mockito.mock(UserRepository::class.java)
    }

    private fun initViewModel() {
        viewModel = LoginFragmentViewModel(
            requestUserActivation = requestUserActivation,
            requestUserInfo = requestUserInfo,
            dispatchersProvider = dispatchersProvider
        )
    }

    @Test
    fun `RequestUserActivation completes successfully`() = runTest {
        // Given
        `when`(repository.requestUserActivation(any())).thenReturn("validToken")
        `when`(repository.requestGetUserInfo()).then {  }
        // When
        // Then
    }
}
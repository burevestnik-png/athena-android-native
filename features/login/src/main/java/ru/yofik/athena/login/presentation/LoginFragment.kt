package ru.yofik.athena.login.presentation

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.common.presentation.components.base.BaseFragment
import ru.yofik.athena.common.presentation.components.extensions.fragment.handleFailures
import ru.yofik.athena.common.presentation.components.extensions.fragment.launchViewModelsFlow
import ru.yofik.athena.common.presentation.components.extensions.fragment.navigate
import ru.yofik.athena.common.presentation.model.UIState
import ru.yofik.athena.common.utils.Route
import ru.yofik.athena.common.utils.Routes
import ru.yofik.athena.login.R
import ru.yofik.athena.login.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment :
    BaseFragment<LoginFragmentViewModel, FragmentLoginBinding>(R.layout.fragment_login) {
    override val binding by viewBinding(FragmentLoginBinding::bind)
    override val viewModel by viewModels<LoginFragmentViewModel>()

    ///////////////////////////////////////////////////////////////////////////
    // SETUPING UI
    ///////////////////////////////////////////////////////////////////////////

    override fun setupUI() {
        setOnCodeChangeListener()
        listenToSubmitButton()
    }

    private fun listenToSubmitButton() {
        binding.submitButton.setOnClickListener { requestUserActivation() }
    }

    private fun setOnCodeChangeListener() {
        binding.codeInput.editText?.doAfterTextChanged { onCodeValueChange(it?.toString() ?: "") }
    }

    ///////////////////////////////////////////////////////////////////////////
    // STATE OBSERVING
    ///////////////////////////////////////////////////////////////////////////

    override fun observeViewState() {
        launchViewModelsFlow { viewModel.state.collect { updateScreenState(it) } }
    }

    private fun updateScreenState(state: UIState<LoginViewStatePayload>) {
        with(binding) {
            progressBar.isVisible = state.loading
            codeInput.error = resources.getString(state.payload.codeError)
            submitButton.isEnabled = state.payload.isSubmitButtonActive
        }

        handleFailures(state.failure)
    }

    ///////////////////////////////////////////////////////////////////////////
    // EFFECT OBSERVING
    ///////////////////////////////////////////////////////////////////////////

    override fun observeViewEffects() {
        launchViewModelsFlow { viewModel.effects.collect { reactTo(it) } }
    }

    private fun reactTo(effect: LoginViewEffect) {
        when (effect) {
            is LoginViewEffect.NavigateToChatListPage -> navigate(Route.build {
                screen = Routes.CHAT_LIST
            })
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // ON EVENT WRAPPERS
    ///////////////////////////////////////////////////////////////////////////

    private fun requestUserActivation() {
        viewModel.onEvent(LoginEvent.RequestUserActivation)
    }

    private fun onCodeValueChange(value: String) {
        viewModel.onEvent(LoginEvent.OnCodeValueChange(value))
    }
}

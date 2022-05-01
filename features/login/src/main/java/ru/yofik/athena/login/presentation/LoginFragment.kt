package ru.yofik.athena.login.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.common.presentation.model.handleFailures
import ru.yofik.athena.common.utils.InternalDeepLink
import ru.yofik.athena.login.databinding.FragmentLoginBinding
import timber.log.Timber

/**
 * A simple [Fragment] subclass. Use the [LoginFragment.newInstance] factory method to create an
 * instance of this fragment.
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!

    private val viewModel by viewModels<LoginFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewStateUpdates()
        observeViewEffects()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUI() {
        setOnCodeChangeListener()
        listenToSubmitButton()
    }

    private fun listenToSubmitButton() {
        binding.submitButton.setOnClickListener { requestUserActivation() }
    }

    private fun setOnCodeChangeListener() {
        binding.codeInput.editText?.doAfterTextChanged { onCodeValueChange(it?.toString() ?: "") }
    }

    private fun observeViewStateUpdates() {
        viewModel.state.observe(viewLifecycleOwner) { updateScreenState(it) }
    }

    private fun updateScreenState(state: LoginViewState) {
        Timber.d("updateScreenState: $state")
        binding.progressBar.isVisible = state.loading
        handleFailures(state.failure)
    }

    private fun observeViewEffects() {
        viewModel.effects.observe(viewLifecycleOwner) { reactTo(it) }
    }

    private fun reactTo(effect: LoginViewEffect) {
        when (effect) {
            is LoginViewEffect.NavigateToChatListPage -> navigateToChatListScreen()
        }
    }

    private fun navigateToChatListScreen() {
        val deepLink = InternalDeepLink.CHAT_LIST.toUri()
        findNavController().navigate(deepLink)
    }

    private fun onCodeValueChange(value: String) {
        viewModel.onEvent(LoginEvent.OnCodeValueChange(value))
    }

    private fun requestUserActivation() {
        viewModel.onEvent(LoginEvent.RequestUserActivation)
    }

    companion object {
        @JvmStatic fun newInstance() = LoginFragment()
    }
}

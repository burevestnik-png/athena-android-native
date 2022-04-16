package ru.yofik.athena.login.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.common.WorkspaceActivity
import ru.yofik.athena.common.presentation.FailureEvent
import ru.yofik.athena.common.presentation.model.handleFailures
import ru.yofik.athena.databinding.FragmentLoginBinding
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUI() {
        setOnCodeChangeListener()
        setOnSubmitButtonClickListener()
        observeViewStateUpdates()
    }

    private fun setOnSubmitButtonClickListener() {
        binding.submitButton.setOnClickListener { requestUserActivation() }
    }

    private fun setOnCodeChangeListener() {
        binding.codeInput.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun afterTextChanged(s: Editable?) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    onCodeValueChange(s?.toString() ?: "")
                }
            }
        )
    }

    private fun observeViewStateUpdates() {
        viewModel.state.observe(viewLifecycleOwner) { updateScreenState(it) }
    }

    private fun updateScreenState(state: LoginViewState) {
        Timber.d("updateScreenState: $state")
        binding.progressBar.isVisible = state.loading
        handleNavigateToChatListScreen(state.shouldNavigateToChatListScreen)
        handleFailures(state.failure)
    }

    private fun handleNavigateToChatListScreen(shouldNavigate: Boolean) {
        if (shouldNavigate) {
            requireActivity().apply { startActivity(WorkspaceActivity.intentOf(this)) }
        }
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

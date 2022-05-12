package ru.yofik.athena.settings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.common.domain.model.user.User
import ru.yofik.athena.common.presentation.views.AvatarView
import ru.yofik.athena.common.utils.InternalDeepLink
import ru.yofik.athena.settings.R
import ru.yofik.athena.settings.databinding.FragmentSettingsBinding

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding!!

    private val viewModel by viewModels<SettingsFragmentViewModel>()

    private val actionBar: ActionBar
        get() =
            (activity as AppCompatActivity).supportActionBar
                ?: throw RuntimeException("View was initialized wrong")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewEffects()
        observeViewStateUpdates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {
        setupActionBar()
        listenToLogoutButton()
    }

    private fun listenToLogoutButton() {
        binding.logoutButton.setOnClickListener {
            viewModel.onEvent(SettingsFragmentEvent.LogoutUser)
        }
    }

    private fun setupActionBar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarWrapper.toolbar)
        actionBar.title = ""
    }

    private fun observeViewStateUpdates() {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it)
        }
    }

    private fun updateScreenState(state: SettingsViewState) {
        binding.progressBar.isVisible = state.loading
    }

    private fun observeViewEffects() {
        viewModel.effects.observe(viewLifecycleOwner) { reactTo(it) }
    }

    private fun reactTo(effect: SettingsFragmentViewEffect) {
        when (effect) {
            is SettingsFragmentViewEffect.NavigateToLoginScreen -> navigateToLoginScreen()
            is SettingsFragmentViewEffect.ProvideUserInfo -> handleProvidingUserInfo(effect.user)
        }
    }

    private fun handleProvidingUserInfo(user: User) {
        binding.apply {
            userName.text = user.name
            //todo rework
            userLogin.text = "@${user.login}"
            avatar.setText(user.name)
        }
    }

    private fun navigateToLoginScreen() {
        val deepLink = InternalDeepLink.LOGIN.toUri()
        findNavController().navigate(deepLink)
    }
}
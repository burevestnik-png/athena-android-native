package ru.yofik.athena.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.common.domain.model.user.User
import ru.yofik.athena.common.utils.Routes
import ru.yofik.athena.profile.R
import ru.yofik.athena.profile.databinding.FragmentSettingsBinding

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding!!

    private val viewModel by viewModels<ProfileFragmentViewModel>()

    private val actionBar: ActionBar
        get() =
            (activity as AppCompatActivity).supportActionBar
                ?: throw RuntimeException("View was initialized wrong")

    ///////////////////////////////////////////////////////////////////////////
    // LIFECYCLE
    ///////////////////////////////////////////////////////////////////////////

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

    ///////////////////////////////////////////////////////////////////////////
    // SETUPING UI
    ///////////////////////////////////////////////////////////////////////////

    private fun setupUI() {
        setupActionBar()
        listenToLogoutButton()
    }

    private fun listenToLogoutButton() {
        binding.logoutButton.setOnClickListener {
            viewModel.onEvent(ProfileFragmentEvent.LogoutUser)
        }
    }

    private fun setupActionBar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarWrapper.toolbar)
        actionBar.title = ""
    }

    ///////////////////////////////////////////////////////////////////////////
    // OBSERVE STATE
    ///////////////////////////////////////////////////////////////////////////

    private fun observeViewStateUpdates() {
        viewModel.state.observe(viewLifecycleOwner) { updateScreenState(it) }
    }

    private fun updateScreenState(state: ProfileViewState) {
        binding.progressBar.isVisible = state.loading
    }

    ///////////////////////////////////////////////////////////////////////////
    // OBSERVE EFFECTS
    ///////////////////////////////////////////////////////////////////////////

    private fun observeViewEffects() {
        viewModel.effects.observe(viewLifecycleOwner) { reactTo(it) }
    }

    private fun reactTo(effect: ProfileFragmentViewEffect) {
        when (effect) {
            is ProfileFragmentViewEffect.NavigateToLoginScreen -> navigateToLoginScreen()
            is ProfileFragmentViewEffect.ProvideUserInfo -> handleProvidingUserInfo(effect.user)
        }
    }

    private fun handleProvidingUserInfo(user: User) {
        binding.apply {
            userName.text = user.name
            userLogin.text = getString(R.string.user_login, user.login)
            avatar.setText(user.name)
        }
    }

    private fun navigateToLoginScreen() {
        val deepLink = Routes.LOGIN.toUri()
        findNavController().navigate(deepLink)
    }
}

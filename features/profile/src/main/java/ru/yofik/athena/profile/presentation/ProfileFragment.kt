package ru.yofik.athena.profile.presentation

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.common.domain.model.user.User
import ru.yofik.athena.common.presentation.components.base.BaseFragment
import ru.yofik.athena.common.presentation.components.extensions.launchViewModelsFlow
import ru.yofik.athena.common.presentation.model.EmptyPayload
import ru.yofik.athena.common.presentation.model.UIState
import ru.yofik.athena.common.utils.Routes
import ru.yofik.athena.profile.R
import ru.yofik.athena.profile.databinding.FragmentSettingsBinding

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<ProfileFragmentViewModel, FragmentSettingsBinding>(R.layout.fragment_settings) {
    override val binding by viewBinding(FragmentSettingsBinding::bind)
    override val viewModel by viewModels<ProfileFragmentViewModel>()

    private val actionBar: ActionBar
        get() =
            (activity as AppCompatActivity).supportActionBar
                ?: throw RuntimeException("View was initialized wrong")

    ///////////////////////////////////////////////////////////////////////////
    // SETUPING UI
    ///////////////////////////////////////////////////////////////////////////

    override fun setupUI() {
        setupActionBar()
        listenToLogoutButton()
    }

    private fun listenToLogoutButton() {
        binding.logoutButton.setOnClickListener { requestLogoutUser() }
    }

    private fun setupActionBar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarWrapper.toolbar)
        actionBar.title = ""
    }

    ///////////////////////////////////////////////////////////////////////////
    // OBSERVE STATE
    ///////////////////////////////////////////////////////////////////////////

    override fun observeViewState() {
        launchViewModelsFlow { viewModel.state.collect { updateScreenState(it) } }
    }

    private fun updateScreenState(state: UIState<EmptyPayload>) {
        binding.progressBar.isVisible = state.loading
    }

    ///////////////////////////////////////////////////////////////////////////
    // OBSERVE EFFECTS
    ///////////////////////////////////////////////////////////////////////////

    override fun observeViewEffects() {
        launchViewModelsFlow { viewModel.effects.collect { reactTo(it) } }
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

    ///////////////////////////////////////////////////////////////////////////
    // ON EVENT WRAPPERS
    ///////////////////////////////////////////////////////////////////////////

    private fun requestLogoutUser() {
        viewModel.onEvent(ProfileFragmentEvent.LogoutUser)
    }
}

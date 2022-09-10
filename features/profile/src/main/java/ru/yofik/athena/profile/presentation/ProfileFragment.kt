package ru.yofik.athena.profile.presentation

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.common.domain.model.users.User
import ru.yofik.athena.common.presentation.components.base.BaseFragment
import ru.yofik.athena.common.presentation.components.extensions.launchViewModelsFlow
import ru.yofik.athena.common.presentation.components.extensions.navigate
import ru.yofik.athena.common.presentation.model.EmptyPayload
import ru.yofik.athena.common.presentation.model.UIState
import ru.yofik.athena.common.utils.Routes
import ru.yofik.athena.profile.R
import ru.yofik.athena.profile.databinding.FragmentProfileBinding

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<ProfileFragmentViewModel, FragmentProfileBinding>(R.layout.fragment_profile) {
    override val binding by viewBinding(FragmentProfileBinding::bind)
    override val viewModel by viewModels<ProfileFragmentViewModel>()

    ///////////////////////////////////////////////////////////////////////////
    // SETUPING UI
    ///////////////////////////////////////////////////////////////////////////

    override fun setupUI() {
        listenToLogoutButton()
    }

    private fun listenToLogoutButton() {
        binding.logoutButton.setOnClickListener { requestLogoutUser() }
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
        launchViewModelsFlow {
            viewModel.effects.collect {
                reactTo(it)
            }
        }
    }

    private fun reactTo(effect: ProfileFragmentViewEffect) {
        when (effect) {
            is ProfileFragmentViewEffect.NavigateToLoginScreen -> navigate(Routes.LOGIN)
            is ProfileFragmentViewEffect.ProvideUserInfo -> handleProvidingUserInfo(effect.user)
        }
    }

    private fun handleProvidingUserInfo(user: User) {
        binding.apply {
            userName.text = user.name
            userLogin.text = getString(R.string.user_login, user.login)
            avatar.text = user.name
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // ON EVENT WRAPPERS
    ///////////////////////////////////////////////////////////////////////////

    private fun requestLogoutUser() {
        viewModel.onEvent(ProfileFragmentEvent.LogoutUser)
    }
}

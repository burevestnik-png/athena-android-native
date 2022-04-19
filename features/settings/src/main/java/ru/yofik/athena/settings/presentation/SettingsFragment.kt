package ru.yofik.athena.settings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.common.utils.InternalDeepLink
import ru.yofik.athena.settings.databinding.FragmentSettingsBinding

/**
 * A simple [Fragment] subclass. Use the [SettingsFragment.newInstance] factory method to create an
 * instance of this fragment.
 */
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
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar.toolbar)
        actionBar.title = ""
    }

    private fun observeViewEffects() {
        viewModel.effects.observe(viewLifecycleOwner) { reactTo(it) }
    }

    private fun reactTo(effect: SettingsFragmentViewEffect) {
        when (effect) {
            is SettingsFragmentViewEffect.NavigateToLoginScreen -> navigateToLoginScreen()
        }
    }

    private fun navigateToLoginScreen() {
        val deepLink = InternalDeepLink.LOGIN.toUri()
        findNavController().navigate(deepLink)
    }
}

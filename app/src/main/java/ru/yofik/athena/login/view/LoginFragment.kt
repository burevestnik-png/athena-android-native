package ru.yofik.athena.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.common.WorkspaceActivity
import ru.yofik.athena.databinding.FragmentLoginBinding
import ru.yofik.athena.login.viewmodel.LoginViewModel
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

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitButton.setOnClickListener {
            viewModel.activate()
//            requireActivity().startActivity(WorkspaceActivity.intentOf(requireActivity()))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUI() {
        binding.apply { vm = viewModel }
    }

    companion object {
        @JvmStatic fun newInstance() = LoginFragment()
    }
}

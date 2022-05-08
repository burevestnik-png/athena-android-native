package ru.yofik.athena.createchat.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.common.presentation.model.handleFailures
import ru.yofik.athena.common.utils.InternalDeepLink
import ru.yofik.athena.createchat.databinding.FragmentCreateChatBinding
import timber.log.Timber

@AndroidEntryPoint
class CreateChatFragment : Fragment() {
    private var _binding: FragmentCreateChatBinding? = null
    private val binding: FragmentCreateChatBinding
        get() = _binding!!

    private val viewModel by viewModels<CreateChatFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        requestGetAllUsers()
        observeViewEffects()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        setupSwipeRefreshLayout()

        listenToBackButtonClick()

        observeViewStateUpdates(adapter)
    }

    private fun observeViewEffects() {
        viewModel.effects.observe(viewLifecycleOwner) { reactTo(it) }
    }

    private fun reactTo(effect: CreateChatFragmentViewEffect) {
        when (effect) {
            is CreateChatFragmentViewEffect.NavigateToChatListScreen -> navigateToChatListScreen()
        }
    }

    private fun listenToBackButtonClick() {
        binding.toolbar.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun navigateToChatListScreen() {
        val deepLink = InternalDeepLink.CHAT_LIST.toUri()
        findNavController().navigate(deepLink)
    }

    private fun observeViewStateUpdates(adapter: UserAdapter) {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it, adapter)
            Timber.d("new event: $it")
        }
    }

    private fun updateScreenState(state: CreateChatViewState, adapter: UserAdapter) {
        binding.swipeLayout.isRefreshing = state.loading
        adapter.submitList(state.users)
        handleFailures(state.failure)
    }

    private fun setupRecyclerView(adapter: UserAdapter) {
        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
            // todo add in future
            // setHasFixedSize(true)
        }
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeLayout.apply { setOnRefreshListener { requestGetAllUsers() } }
    }

    private fun createAdapter(): UserAdapter {
        return UserAdapter().apply {
            setUserClickListener { id, name -> requestCreateChat(id, name) }
        }
    }

    private fun requestGetAllUsers() {
        viewModel.onEvent(CreateChatEvent.GetAllUsers)
    }

    private fun requestCreateChat(id: Long, name: String) {
        viewModel.onEvent(CreateChatEvent.CreateChat(id, name))
    }
}

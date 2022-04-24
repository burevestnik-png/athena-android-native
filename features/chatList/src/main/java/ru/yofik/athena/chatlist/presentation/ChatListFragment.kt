package ru.yofik.athena.chatlist.presentation

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
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.chatList.databinding.FragmentChatListBinding
import ru.yofik.athena.common.presentation.model.handleFailures
import ru.yofik.athena.common.utils.InternalDeepLink
import timber.log.Timber

@AndroidEntryPoint
class ChatListFragment : Fragment() {
    private var _binding: FragmentChatListBinding? = null
    private val binding
        get() = _binding!!

    private val actionBar: ActionBar
        get() =
            (activity as AppCompatActivity).supportActionBar
                ?: throw RuntimeException("View was initialized wrong")

    private val viewModel: ChatListFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        requestGetAllChats()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {
        setupActionBar()
        listenToAddButton()

        val adapter = createAdapter()
        setupRecyclerView(adapter)
        observeViewStateUpdates(adapter)
    }

    private fun observeViewStateUpdates(adapter: ChatAdapter) {
        viewModel.state.observe(viewLifecycleOwner) { updateScreenState(it, adapter) }
    }

    private fun updateScreenState(state: ChatListViewState, adapter: ChatAdapter) {
        Timber.d("updateScreenState: $state")
        binding.progressBar.isVisible = state.loading
        adapter.submitList(state.chats)
        handleFailures(state.failure)
    }

    private fun createAdapter(): ChatAdapter {
        return ChatAdapter().apply { setChatClickListener { navigateToChatScreen(it) } }
    }

    private fun setupRecyclerView(adapter: ChatAdapter) {
        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            // todo add in future
            // setHasFixedSize(true)
        }
    }

    private fun navigateToChatScreen(id: Long) {
        val deepLink = InternalDeepLink.createChatDeepLink(id)
        Timber.d("navigateToChatScreen: $deepLink")
        findNavController().navigate(deepLink)
    }

    private fun listenToAddButton() {
        binding.toolbar.addButton.setOnClickListener { navigateToCreateChatScreen() }
    }

    private fun setupActionBar() {
        // Adding Toolbar & removing showing app name in title
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar.root)
        actionBar.title = ""
    }

    private fun navigateToCreateChatScreen() {
        val deepLink = InternalDeepLink.CREATE_CHAT.toUri()
        findNavController().navigate(deepLink)
    }

    private fun requestGetAllChats() {
        viewModel.onEvent(ChatListEvent.GetAllChats)
    }
}

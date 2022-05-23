package ru.yofik.athena.chatlist.presentation

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.chatList.R
import ru.yofik.athena.chatList.databinding.FragmentChatListBinding
import ru.yofik.athena.common.presentation.components.BaseFragment
import ru.yofik.athena.common.presentation.handleFailures
import ru.yofik.athena.common.utils.InternalDeepLink
import timber.log.Timber

@AndroidEntryPoint
class ChatListFragment :
    BaseFragment<ChatListFragmentViewModel, FragmentChatListBinding>(R.layout.fragment_chat_list) {
    override val binding: FragmentChatListBinding by viewBinding(FragmentChatListBinding::bind)
    override val viewModel: ChatListFragmentViewModel by viewModels()

    private val actionBar: ActionBar
        get() =
            (activity as AppCompatActivity).supportActionBar
                ?: throw RuntimeException("View was initialized wrong")

    override fun setupUI() {
        setupActionBar()
        listenToAddButton()
        setupSwipeRefreshLayout()

        val adapter = createAdapter()
        setupRecyclerView(adapter)
        observeViewStateUpdates(adapter)
    }

    private fun observeViewStateUpdates(adapter: ChatAdapter) {
        viewModel.state.observe(viewLifecycleOwner) { updateScreenState(it, adapter) }
    }

    private fun updateScreenState(state: ChatListViewState, adapter: ChatAdapter) {
        Timber.d("updateScreenState: $state")
        binding.swipeLayout.isRefreshing = state.loading
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

    private fun setupSwipeRefreshLayout() {
        binding.swipeLayout.setOnRefreshListener { requestGetAllChats() }
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
        //        viewModel.onEvent(ChatListEvent.GetAllChats)
    }
}

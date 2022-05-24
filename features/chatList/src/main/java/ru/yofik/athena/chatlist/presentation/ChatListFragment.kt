package ru.yofik.athena.chatlist.presentation

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.chatList.R
import ru.yofik.athena.chatList.databinding.FragmentChatListBinding
import ru.yofik.athena.common.presentation.components.BaseFragment
import ru.yofik.athena.common.presentation.components.handleFailures
import ru.yofik.athena.common.presentation.components.launchViewModelsFlow
import ru.yofik.athena.common.presentation.components.navigate
import ru.yofik.athena.common.utils.Routes
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

    private lateinit var adapter: ChatAdapter

    ///////////////////////////////////////////////////////////////////////////
    // SETUPING UI
    ///////////////////////////////////////////////////////////////////////////

    override fun setupUI() {
        setupActionBar()
        setupSwipeRefreshLayout()

        listenToAddButton()

        adapter = createAdapter()
        setupRecyclerView(adapter)
    }

    private fun setupActionBar() {
        // Adding Toolbar & removing showing app name in title
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar.root)
        actionBar.title = ""
    }

    private fun listenToAddButton() {
        binding.toolbar.addButton.setOnClickListener { navigate(Routes.CREATE_CHAT) }
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeLayout.setOnRefreshListener { requestForceGetAllChats() }
    }

    private fun requestForceGetAllChats() {
        viewModel.onEvent(ChatListEvent.ForceGetAllChats)
    }

    private fun createAdapter(): ChatAdapter {
        return ChatAdapter().apply { setChatClickListener { id -> navigate(Routes.CHAT(id)) } }
    }

    private fun setupRecyclerView(adapter: ChatAdapter) {
        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            // todo add in future
            // setHasFixedSize(true)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // STATE OBSERVING
    ///////////////////////////////////////////////////////////////////////////

    override fun observeViewState() {
        launchViewModelsFlow {
            viewModel.state.collect { updateScreenState(it) }
        }
    }

    private fun updateScreenState(state: ChatListViewState) {
        Timber.d("updateScreenState: $state")
        binding.swipeLayout.isRefreshing = state.loading
        adapter.submitList(state.chats)
        handleFailures(state.failure)
    }
}

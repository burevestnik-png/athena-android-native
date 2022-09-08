package ru.yofik.athena.chatlist.presentation

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.chatList.R
import ru.yofik.athena.chatList.databinding.FragmentChatListBinding
import ru.yofik.athena.chatlist.presentation.recyclerview.ChatAdapter
import ru.yofik.athena.common.presentation.components.base.BaseFragment
import ru.yofik.athena.common.presentation.components.extensions.handleFailures
import ru.yofik.athena.common.presentation.components.extensions.launchViewModelsFlow
import ru.yofik.athena.common.presentation.components.extensions.navigate
import ru.yofik.athena.common.presentation.model.UIState
import ru.yofik.athena.common.presentation.utils.InfiniteScrollListener
import ru.yofik.athena.common.utils.Routes
import timber.log.Timber

// todo как вынести пагинацию

@AndroidEntryPoint
class ChatListFragment :
    BaseFragment<ChatListFragmentViewModel, FragmentChatListBinding>(R.layout.fragment_chat_list) {

    override val binding: FragmentChatListBinding by viewBinding(FragmentChatListBinding::bind)
    override val viewModel: ChatListFragmentViewModel by viewModels()

    private lateinit var adapter: ChatAdapter

    ///////////////////////////////////////////////////////////////////////////
    // SETUPING UI
    ///////////////////////////////////////////////////////////////////////////

    override fun setupUI() {
        adapter = setupChatAdapter()
        setupRecyclerView(adapter)
        setHasOptionsMenu(true)

        listenToListPulling()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_create_chat -> {
                navigate(Routes.CREATE_CHAT)
                true
            }
            else -> false
        }
    }

    private fun listenToListPulling() {
        binding.swipeLayout.setOnRefreshListener { requestForceGetAllChats() }
    }

    private fun setupChatAdapter() =
        ChatAdapter().apply { setChatClickListener { id -> navigate(Routes.CHAT(id)) } }

    private fun setupRecyclerView(adapter: ChatAdapter) {
        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            // todo add in future
            // setHasFixedSize(true)

            addOnScrollListener(createOnScrollListener(layoutManager as LinearLayoutManager))
        }
    }

    private fun createOnScrollListener(
        layoutManager: LinearLayoutManager
    ): RecyclerView.OnScrollListener {
        return object :
            InfiniteScrollListener(layoutManager, ChatListFragmentViewModel.UI_PAGE_SIZE) {
            override fun loadMoreItems() = requestMoreUsers()
            override fun isLastPage(): Boolean = viewModel.isLastPage
            override fun isLoading(): Boolean = viewModel.state.value.loading
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // STATE OBSERVING
    ///////////////////////////////////////////////////////////////////////////

    override fun observeViewState() {
        launchViewModelsFlow { viewModel.state.collect { updateScreenState(it) } }
    }

    private fun updateScreenState(state: UIState<ChatListFragmentPayload>) {
        Timber.d("updateScreenState: $state")
        binding.swipeLayout.isRefreshing = state.loading
        adapter.setChats(state.payload.chats)
        handleFailures(state.failure)
    }

    ///////////////////////////////////////////////////////////////////////////
    // ON EVENT WRAPPERS
    ///////////////////////////////////////////////////////////////////////////

    private fun requestMoreUsers() {
        viewModel.onEvent(ChatListEvent.RequestNextChatsPage)
    }

    private fun requestForceGetAllChats() {
        viewModel.onEvent(ChatListEvent.ForceGetAllChats)
    }
}

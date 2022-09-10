package ru.yofik.athena.chatlist.presentation

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
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
        setupMenu()

        listenToListPulling()
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.toolbar_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
                    when (menuItem.itemId) {
                        R.id.action_create_chat -> {
                            navigate(Routes.CREATE_CHAT)
                            true
                        }
                        else -> false
                    }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }

    private fun listenToListPulling() {
        binding.swipeLayout.setOnRefreshListener { requestForceGetAllChats() }
    }

    private fun setupChatAdapter() =
        ChatAdapter(
            chatNavigateListener = { id -> navigate(Routes.CHAT(id)) },
            chatSelectionListener = { id -> requestAddChatToSelection(id) }
        )

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

        adapter.apply {
            updateChats(state.payload.chats)
            currentScreenMode = state.payload.mode
        }

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

    private fun requestAddChatToSelection(id: Long) {
        viewModel.onEvent(ChatListEvent.AddChatToSelection(id))
    }

    private fun requestCancelSelection() {
        viewModel.onEvent(ChatListEvent.CancelSelection)
    }
}

package ru.yofik.athena.chatlist.presentation

import androidx.appcompat.view.ActionMode
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.chatList.R
import ru.yofik.athena.chatList.databinding.FragmentChatListBinding
import ru.yofik.athena.chatlist.presentation.contextualAppBar.ContextualAppBar
import ru.yofik.athena.chatlist.presentation.recyclerview.ChatAdapter
import ru.yofik.athena.common.presentation.components.base.BaseFragment
import ru.yofik.athena.common.presentation.components.extensions.fragment.*
import ru.yofik.athena.common.presentation.model.UIState
import ru.yofik.athena.common.presentation.utils.InfiniteScrollListener
import ru.yofik.athena.common.utils.Routes
import timber.log.Timber

@AndroidEntryPoint
class ChatListFragment :
    BaseFragment<ChatListFragmentViewModel, FragmentChatListBinding>(R.layout.fragment_chat_list) {

    override val binding: FragmentChatListBinding by viewBinding(FragmentChatListBinding::bind)
    override val viewModel: ChatListFragmentViewModel by viewModels()

    private lateinit var adapter: ChatAdapter
    private var actionMode: ActionMode? = null

    private val defaultModeMenu =
        createMenuProvider(R.menu.default_mode_menu) {
            when (it.itemId) {
                R.id.action_find_chat -> {
                    true
                }
                else -> false
            }
        }

    private val contextualAppBar =
        ContextualAppBar(
            onDeleteChats = { Timber.d(": ON DELETE") },
            onDestroyActionMode = { requestCancelSelection() }
        )

    ///////////////////////////////////////////////////////////////////////////
    // SETUPING UI
    ///////////////////////////////////////////////////////////////////////////

    override fun setupUI() {
        adapter = setupChatAdapter()
        setupRecyclerView(adapter)

        addMenuProvider(defaultModeMenu)

        listenToFabClick()
        listenToListPulling()
    }

    private fun listenToFabClick() {
        binding.floatingActionButton.setOnClickListener {
            navigate(Routes.CREATE_CHAT)
            actionMode?.finish()
            actionMode = null
        }
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
        layoutManager: LinearLayoutManager,
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

        contextualAppBar.updateSelectedChatsAmount(state.payload.selectedChatsAmount)
        if (state.payload.mode == ChatListFragmentPayload.Mode.SELECTION && actionMode == null) {
            actionMode = requireAppCompatActivity().startSupportActionMode(contextualAppBar)
        }

        if (state.payload.mode == ChatListFragmentPayload.Mode.DEFAULT && actionMode != null) {
            actionMode?.finish()
            actionMode = null
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

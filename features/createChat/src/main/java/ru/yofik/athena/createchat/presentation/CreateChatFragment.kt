package ru.yofik.athena.createchat.presentation

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.common.presentation.components.BaseFragment
import ru.yofik.athena.common.presentation.components.handleFailures
import ru.yofik.athena.common.presentation.components.launchViewModelsFlow
import ru.yofik.athena.common.presentation.components.navigate
import ru.yofik.athena.common.utils.Routes
import ru.yofik.athena.createchat.R
import ru.yofik.athena.createchat.databinding.FragmentCreateChatBinding

@AndroidEntryPoint
class CreateChatFragment :
    BaseFragment<CreateChatFragmentViewModel, FragmentCreateChatBinding>(
        R.layout.fragment_create_chat
    ) {
    override val binding by viewBinding(FragmentCreateChatBinding::bind)
    override val viewModel by viewModels<CreateChatFragmentViewModel>()

    private lateinit var adapter: UserAdapter

    ///////////////////////////////////////////////////////////////////////////
    // SETUPING UI
    ///////////////////////////////////////////////////////////////////////////

    override fun setupUI() {
        adapter = createAdapter()
        setupRecyclerView(adapter)

        setupSwipeRefreshLayout()
        listenToBackButtonClick()
    }

    private fun createAdapter(): UserAdapter {
        return UserAdapter().apply {
            setUserClickListener { id, name -> requestCreateChat(id, name) }
        }
    }

    private fun requestCreateChat(id: Long, name: String) {
        viewModel.onEvent(CreateChatEvent.CreateChat(id, name))
    }

    private fun setupRecyclerView(adapter: UserAdapter) {
        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
            // todo add in future
            // setHasFixedSize(true)
            addOnScrollListener(createOnScrollListener(layoutManager as LinearLayoutManager))
        }
    }

    private fun createOnScrollListener(
        layoutManager: LinearLayoutManager
    ): RecyclerView.OnScrollListener {
        return object :
            InfiniteScrollListener(layoutManager, CreateChatFragmentViewModel.UI_PAGE_SIZE) {
            override fun loadMoreItems() = requestMoreUsers()
            override fun isLastPage(): Boolean = viewModel.isLastPage
            override fun isLoading(): Boolean = viewModel.state.value.loading
        }
    }

    private fun requestMoreUsers() {
        viewModel.onEvent(CreateChatEvent.RequestMoreUsers)
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeLayout.apply { setOnRefreshListener {} }
    }

    private fun listenToBackButtonClick() {
        binding.toolbar.backButton.setOnClickListener { findNavController().popBackStack() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // STATE OBSERVING
    ///////////////////////////////////////////////////////////////////////////

    override fun observeViewState() {
        launchViewModelsFlow { viewModel.state.collect { updateScreenState(it) } }
    }

    private fun updateScreenState(state: CreateChatViewState) {
        binding.swipeLayout.isRefreshing = state.loading
        adapter.submitList(state.users)
        handleFailures(state.failure)
    }

    ///////////////////////////////////////////////////////////////////////////
    // EFFECT OBSERVING
    ///////////////////////////////////////////////////////////////////////////

    override fun observeViewEffects() {
        launchViewModelsFlow { viewModel.effects.collect { reactTo(it) } }
    }

    private fun reactTo(effect: CreateChatFragmentViewEffect) {
        when (effect) {
            is CreateChatFragmentViewEffect.NavigateToChatListScreen -> navigate(Routes.CHAT_LIST)
        }
    }
}

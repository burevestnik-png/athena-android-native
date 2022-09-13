package ru.yofik.athena.chat.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.chat.R
import ru.yofik.athena.chat.databinding.FragmentChatBinding
import ru.yofik.athena.common.presentation.components.base.BaseFragment
import ru.yofik.athena.common.presentation.components.extensions.fragment.handleFailures
import ru.yofik.athena.common.presentation.components.extensions.fragment.launchViewModelsFlow
import ru.yofik.athena.common.presentation.model.UIState
import timber.log.Timber

private const val ARG_ID = "id"

@AndroidEntryPoint
class ChatFragment :
    BaseFragment<ChatFragmentViewModel, FragmentChatBinding>(R.layout.fragment_chat) {
    override val viewModel by viewModels<ChatFragmentViewModel>()
    override val binding by viewBinding(FragmentChatBinding::bind)

    private lateinit var adapter: MessageAdapter

    private var id: Long? = null

    ///////////////////////////////////////////////////////////////////////////
    // LIFECYCLE
    ///////////////////////////////////////////////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { id = it.getLong(ARG_ID) }
    }

    ///////////////////////////////////////////////////////////////////////////
    // INITIAL REQUESTS
    ///////////////////////////////////////////////////////////////////////////

    override fun processInitialRequests() {
        requestGetChat(id!!)
    }

    ///////////////////////////////////////////////////////////////////////////
    // SETUPPING UI
    ///////////////////////////////////////////////////////////////////////////

    override fun setupUI() {
        adapter = createAdapter()
        setupRecycleView(adapter)
        setupMenu()

        listenToMessageInput()
        listenToSubmitButtonClick()
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.tool_bar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
                R.id.action_profile -> {
                    true
                }
                else -> false
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun createAdapter(): MessageAdapter {
        return MessageAdapter()
    }

    private fun setupRecycleView(adapter: MessageAdapter) {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context).apply { stackFromEnd = true }
            this.adapter = adapter

            // this callback provide auto scroll messages
            addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                // todo think about useless scroll
                //                if (bottom < oldBottom) {
                if (adapter.itemCount > 0) {
                    postDelayed({ smoothScrollToPosition(adapter.itemCount - 1) }, 100)
                }
                //                }
            }
        }
    }

    private fun listenToMessageInput() {
        binding.input.addTextChangedListener { requestUpdateInput(it.toString()) }
    }

    private fun listenToSubmitButtonClick() {
        binding.sendButton.setOnClickListener { requestSendMessage() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // OBSERVING EFFECTS
    ///////////////////////////////////////////////////////////////////////////

    override fun observeViewEffects() {
        launchViewModelsFlow { viewModel.effects.collect { reactTo(it) } }
    }

    private fun reactTo(effect: ChatFragmentViewEffect) {
        when (effect) {
            is ChatFragmentViewEffect.SetChatName -> handleSetChatName(effect.name)
            is ChatFragmentViewEffect.ClearInput -> handleClearInput()
        }
    }

    private fun handleClearInput() {
        binding.input.setText("")
    }

    private fun handleSetChatName(name: String) {
        (activity as AppCompatActivity).supportActionBar?.title = name
    }

    ///////////////////////////////////////////////////////////////////////////
    // STATE OBSERVING
    ///////////////////////////////////////////////////////////////////////////

    override fun observeViewState() {
        launchViewModelsFlow { viewModel.state.collect { updateScreenState(it) } }
    }

    private fun updateScreenState(state: UIState<ChatFragmentPayload>) {
        Timber.d("updateScreenState: $state")
        binding.progressBar.isVisible = state.loading
        adapter.submitList(state.payload.messages)
        handleFailures(state.failure)
    }

    ///////////////////////////////////////////////////////////////////////////
    // ON EVENT WRAPPERS
    ///////////////////////////////////////////////////////////////////////////

    private fun requestUpdateInput(value: String) {
        viewModel.onEvent(ChatFragmentEvent.UpdateInput(value))
    }

    private fun requestGetChat(id: Long) {
        viewModel.onEvent(ChatFragmentEvent.GetChatInfo(id))
    }

    private fun requestSendMessage() {
        viewModel.onEvent(ChatFragmentEvent.SendMessage)
    }
}

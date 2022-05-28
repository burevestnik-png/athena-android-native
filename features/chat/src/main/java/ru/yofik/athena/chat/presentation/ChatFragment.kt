package ru.yofik.athena.chat.presentation

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.chat.R
import ru.yofik.athena.chat.databinding.FragmentChatBinding
import ru.yofik.athena.common.presentation.components.base.BaseFragment
import ru.yofik.athena.common.presentation.components.extensions.handleFailures
import ru.yofik.athena.common.presentation.components.extensions.launchViewModelsFlow
import ru.yofik.athena.common.presentation.model.UIState
import timber.log.Timber

private const val ARG_ID = "id"

@AndroidEntryPoint
class ChatFragment :
    BaseFragment<ChatFragmentViewModel, FragmentChatBinding>(R.layout.fragment_chat) {
    override val viewModel by viewModels<ChatFragmentViewModel>()
    override val binding by viewBinding(FragmentChatBinding::bind)

    private lateinit var adapter: MessageAdapter
    private val actionBar: ActionBar
        get() =
            (activity as AppCompatActivity).supportActionBar
                ?: throw RuntimeException("View was initialized wrong")

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

        setupActionBar()
        listenToInput()
        listenToSubmitButton()
        listenToBackButtonClick()
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

    private fun listenToInput() {
        binding.input.addTextChangedListener { requestUpdateInput(it.toString()) }
    }

    private fun listenToSubmitButton() {
        binding.sendButton.setOnClickListener { requestSendMessage() }
    }

    private fun setupActionBar() {
        // Adding Toolbar & removing showing app name in title
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar.root)
        actionBar.title = ""
    }

    private fun listenToBackButtonClick() {
        binding.toolbar.backButton.setOnClickListener { findNavController().popBackStack() }
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
        binding.toolbar.chatName.text = name
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

package ru.yofik.athena.chatlist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.chatList.databinding.FragmentChatListBinding
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.utils.InternalDeepLink

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
        viewModel.onEvent(ChatListEvent.GetAllChats)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {
        setupActionBar()

        setupOnCreateChatButtonListener()

        val adapter = createAdapter()
        setupRecyclerView(adapter)
    }

    private fun createAdapter(): ChatAdapter {
        return ChatAdapter(
            emptyList(),
            object : ChatAdapter.Callbacks {
                override fun onChatSelected(chatView: Chat) {
//                    this@ChatListFragment.parentFragmentManager.commit {
//                        add(R.id.container_fragment, fragment)
//                        addToBackStack(null)
//                    }
                }
            }
        )
    }

    private fun setupRecyclerView(adapter: ChatAdapter) {
        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            // todo add in future
            // setHasFixedSize(true)
        }
    }

    private fun setupOnCreateChatButtonListener() {
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

    companion object {
        fun newInstance(): ChatListFragment {
            return ChatListFragment()
        }
    }
}

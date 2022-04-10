package ru.yofik.athena.chatList.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.R
import ru.yofik.athena.chat.view.ChatFragment
import ru.yofik.athena.chatList.adapter.ChatAdapter
import ru.yofik.athena.common.WorkspaceActivity
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.databinding.FragmentChatListBinding

@AndroidEntryPoint
class ChatListFragment : Fragment() {
    private var _binding: FragmentChatListBinding? = null
    private val binding
        get() = _binding!!

    private val actionBar: ActionBar
        get() =
            (activity as AppCompatActivity).supportActionBar
                ?: throw RuntimeException("View was initialized wrong")

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatListBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {
        // Adding Toolbar & removing showing app name in title
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarWrapper.toolbar)
        actionBar.title = ""

        if (activity != null) {
            (activity as WorkspaceActivity).showBottomNavigation()
        }

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter =
            ChatAdapter(
                listOf(Chat.getLeshaChat()),
                object : ChatAdapter.Callbacks {
                    override fun onChatSelected(chatView: Chat) {
                        val fragment = ChatFragment.newInstance(chatView.name)
                        this@ChatListFragment.parentFragmentManager
                            .beginTransaction()
                            .replace(R.id.container_fragment, fragment)
                            .addToBackStack(null)
                            .commit()
                    }
                }
            )
    }

    companion object {
        fun newInstance(): ChatListFragment {
            return ChatListFragment()
        }
    }
}

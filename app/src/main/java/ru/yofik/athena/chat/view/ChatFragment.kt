package ru.yofik.athena.chat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.chat.adapter.MessageAdapter
import ru.yofik.athena.chat.viewmodel.ChatViewModel
import ru.yofik.athena.common.WorkspaceActivity
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.databinding.FragmentChatBinding
import timber.log.Timber

private const val ARG_NAME = "ru.yofik.athena.chat.view.name"

@AndroidEntryPoint
class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding: FragmentChatBinding
        get() = _binding!!

    private val actionBar: ActionBar
        get() =
            (activity as AppCompatActivity).supportActionBar
                ?: throw RuntimeException("View was initialized wrong")

    private var name: String? = null

    private lateinit var recyclerView: RecyclerView
    private val viewModel by viewModels<ChatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { name = it.getString(ARG_NAME) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.messages.observe(viewLifecycleOwner) {
            Timber.d("onViewCreated: ${it.joinToString()}")
            updateUI(it)
        }

        viewModel.currentMessage.observe(viewLifecycleOwner) { Timber.d("onViewCreated:$it") }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun updateUI(messages: List<Message>) {
        recyclerView.adapter = MessageAdapter(messages)
    }

    private fun setupUI() {
        // Adding Toolbar & removing showing app name in title
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar.toolbar)
        actionBar.title = ""

        if (activity != null) {
            (activity as WorkspaceActivity).hideBottomNavigation()
        }


        binding.lifecycleOwner = this
        binding.apply {
            name = this@ChatFragment.name
            vm = viewModel
        }

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MessageAdapter(viewModel.messages.value ?: emptyList())
    }

    companion object {
        /**
         * Use this factory method to create a new instance of this fragment using the provided
         * parameters.
         *
         * @param name Chat name.
         * @return A new instance of fragment ChatFragment.
         */
        @JvmStatic
        fun newInstance(name: String) =
            ChatFragment().apply { arguments = Bundle().apply { putString(ARG_NAME, name) } }
    }
}

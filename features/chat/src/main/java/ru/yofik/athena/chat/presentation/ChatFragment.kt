package ru.yofik.athena.chat.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.chat.databinding.FragmentChatBinding
import timber.log.Timber

private const val ARG_ID = "ru.yofik.athena.chat.presentation.name"

@AndroidEntryPoint
class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding: FragmentChatBinding
        get() = _binding!!

    private val actionBar: ActionBar
        get() =
            (activity as AppCompatActivity).supportActionBar
                ?: throw RuntimeException("View was initialized wrong")

    private var id: Long? = null

    private val viewModel by viewModels<ChatFragmentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { id = it.getLong(ARG_ID) }
        Timber.d("onCreate: $id")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        requestGetChat(id!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecycleView(adapter)
        setupActionBar()
    }

    private fun createAdapter(): MessageAdapter {
        return MessageAdapter()
    }

    private fun setupRecycleView(adapter: MessageAdapter) {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    private fun setupActionBar() {
        // Adding Toolbar & removing showing app name in title
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar.root)
        actionBar.title = ""
    }

    private fun requestGetChat(id: Long) {
        viewModel.onEvent(ChatFragmentEvent.GetChat(id))
    }

    companion object {
        fun createBundle(id: Long): Bundle {
            return bundleOf(ARG_ID to id)
        }
    }
}

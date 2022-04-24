package ru.yofik.athena.chatlist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.yofik.athena.chatList.databinding.ListItemChatBinding
import ru.yofik.athena.chatlist.domain.model.UiChat
import ru.yofik.athena.common.domain.model.chat.Chat

fun interface ChatClickListener {
    fun onChatClick(id: Long)
}

class ChatAdapter : ListAdapter<UiChat, ChatAdapter.ViewHolder>(UI_CHAT_COMPARATOR) {

    private var chatClickListener: ChatClickListener? = null

    fun setChatClickListener(chatClickListener: ChatClickListener) {
        this.chatClickListener = chatClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ListItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ListItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chat: UiChat) {
            binding.apply {
                chatName.text = chat.name
                message.text = chat.message.content
                time.text = chat.message.time
            }

            binding.root.apply {
                setOnClickListener {
                    chatClickListener?.onChatClick(chat.id)
                }
            }
        }
    }
}

private val UI_CHAT_COMPARATOR =
    object : DiffUtil.ItemCallback<UiChat>() {
        override fun areItemsTheSame(oldItem: UiChat, newItem: UiChat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UiChat, newItem: UiChat): Boolean {
            return oldItem == newItem
        }
    }

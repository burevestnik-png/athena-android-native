package ru.yofik.athena.chatList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.utils.toFormattedString
import ru.yofik.athena.databinding.ListItemChatBinding

private const val TAG = "ChatListAdapter"

class ChatAdapter(private val chats: List<Chat>, private val callbacks: Callbacks) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    interface Callbacks {
        fun onChatSelected(chatView: Chat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ListItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chat = chats[position])
        holder.itemView.setOnClickListener { callbacks.onChatSelected(chats[position]) }
    }

    override fun getItemCount(): Int = chats.size

    inner class ViewHolder(private val binding: ListItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chat: Chat) {
            binding.apply {
                this.chat = chat
                this.timestamp = chat.lastMessage.date.toFormattedString()
            }
        }
    }
}

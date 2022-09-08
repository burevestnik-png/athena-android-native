package ru.yofik.athena.chatlist.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yofik.athena.chatList.databinding.ListItemChatBinding
import ru.yofik.athena.chatlist.domain.model.UiChat
import timber.log.Timber

fun interface ChatClickListener {
    fun onChatClick(id: Long)
}

class ChatAdapter(
    private var chats: List<UiChat> = emptyList(),
    private var chatClickListener: ChatClickListener? = null
) : RecyclerView.Adapter<ChatAdapter.UIChatViewHolder>() {

//    private val

    fun setChatClickListener(chatClickListener: ChatClickListener) {
        this.chatClickListener = chatClickListener
    }

    fun setChats(chats: List<UiChat>) {
        this.chats = chats
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = chats.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UIChatViewHolder =
        UIChatViewHolder(
            ListItemChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UIChatViewHolder, position: Int) =
        holder.bind(chats[position])

    inner class UIChatViewHolder(private val binding: ListItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var uiChat: UiChat

        fun bind(chat: UiChat) {
            uiChat = chat

            binding.apply {
                chatName.text = uiChat.name
                message.text = uiChat.message.content
                time.text = uiChat.message.time
                logo.setText(uiChat.name)
                onChatSelect()
            }

            binding.root.apply {
                setOnClickListener {
                    chatClickListener?.onChatClick(uiChat.id)
                }

                setOnLongClickListener {
                    onChatSelect()
                    true
                }
            }
        }

        private fun onChatSelect() {
            binding.logo.apply {
                if (uiChat.isSelected) setSelectedState() else setUnselectedState()
            }
        }
    }
}

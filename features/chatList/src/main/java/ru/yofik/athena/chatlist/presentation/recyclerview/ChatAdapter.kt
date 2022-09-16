package ru.yofik.athena.chatlist.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yofik.athena.chatList.databinding.ListItemChatBinding
import ru.yofik.athena.chatlist.domain.model.UiChat
import ru.yofik.athena.chatlist.presentation.ChatListFragmentPayload
import ru.yofik.athena.common.presentation.customViews.avatarView.AvatarView

fun interface ChatNavigateListener {
    fun onChatNavigate(id: Long)
}

fun interface ChatSelectionListener {
    fun onChatSelection(id: Long)
}

class ChatAdapter(
    private var chats: List<UiChat> = emptyList(),
    private var chatNavigateListener: ChatNavigateListener,
    private var chatSelectionListener: ChatSelectionListener
) : RecyclerView.Adapter<ChatAdapter.UIChatViewHolder>() {

    var currentScreenMode: ChatListFragmentPayload.Mode = ChatListFragmentPayload.Mode.DEFAULT

    fun updateChats(chats: List<UiChat>) {
        // todo check if chats the same
        this.chats = chats
        // todo add callback
        notifyDataSetChanged()
    }

    ///////////////////////////////////////////////////////////////////////////
    // OVERRIDING BASIC METHODS
    ///////////////////////////////////////////////////////////////////////////

    override fun getItemCount(): Int = chats.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UIChatViewHolder =
        UIChatViewHolder(
            ListItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: UIChatViewHolder, position: Int) =
        holder.bind(chats[position])

    ///////////////////////////////////////////////////////////////////////////
    // VIEWHOLDER
    ///////////////////////////////////////////////////////////////////////////

    inner class UIChatViewHolder(private val binding: ListItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(uiChat: UiChat) {
            binding.apply {
                chatName.text = uiChat.name
                message.text = uiChat.message.content
                time.text = uiChat.message.time
                logo.text = uiChat.name

                setStateBySelection(uiChat.isSelected)
            }

            binding.root.apply {
                when (currentScreenMode) {
                    is ChatListFragmentPayload.Mode.DEFAULT -> {
                        setOnClickListener { chatNavigateListener.onChatNavigate(uiChat.id) }

                        setOnLongClickListener {
                            chatSelectionListener.onChatSelection(uiChat.id)
                            true
                        }
                    }
                    is ChatListFragmentPayload.Mode.SELECTION -> {
                        setOnClickListener { chatSelectionListener.onChatSelection(uiChat.id) }
                    }
                }
            }
        }

        private fun setStateBySelection(isSelected: Boolean) {
            if (isSelected) binding.apply { logo.setState(AvatarView.State.SELECTED) }
            else binding.apply { logo.setState(AvatarView.State.DEFAULT) }
        }
    }
}

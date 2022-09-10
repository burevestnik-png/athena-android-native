package ru.yofik.athena.chatlist.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yofik.athena.chatList.databinding.ListItemChatBinding
import ru.yofik.athena.chatlist.domain.model.UiChat
import ru.yofik.athena.chatlist.presentation.ChatListFragmentPayload
import ru.yofik.athena.common.presentation.customViews.avatarView.AvatarView

fun interface ChatNavigateListener {
    fun onChatClick(id: Long)
}

fun interface ChatSelectionListener {
    fun onChatLongClick(id: Long)
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
        notifyDataSetChanged()
    }

    fun updateChatSelection(id: Long) {
        val chat = chats.find { it.id == id }!!
        val index = chats.indexOf(chat)

        this.chats =
            chats
                .toMutableList()
                .apply {
                    removeAt(index)
                    add(index, chat.copy(isSelected = !chat.isSelected))
                }
                .toList()

        notifyItemChanged(index)
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

                if (uiChat.isSelected) setSelectedState() else setUnselectedState()
            }

            binding.root.apply {
                when (currentScreenMode) {
                    is ChatListFragmentPayload.Mode.DEFAULT -> {
                        setOnClickListener { chatNavigateListener.onChatClick(uiChat.id) }

                        setOnLongClickListener {
                            if (uiChat.isSelected) setUnselectedState() else setSelectedState()
                            updateChatSelection(uiChat.id)
                            true
                        }
                    }
                    is ChatListFragmentPayload.Mode.SELECTION -> {
                        setOnClickListener {
                            if (uiChat.isSelected) setUnselectedState() else setSelectedState()
                            updateChatSelection(uiChat.id)
                        }
                    }
                }
            }
        }

        private fun setSelectedState() {
            binding.logo.setState(AvatarView.State.SELECTED)
        }

        private fun setUnselectedState() {
            binding.logo.setState(AvatarView.State.DEFAULT)
        }
    }
}

package ru.yofik.athena.chat.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.yofik.athena.chat.databinding.ListItemReceiveMessageBinding
import ru.yofik.athena.chat.databinding.ListItemSendMessageBinding
import ru.yofik.athena.chat.domain.model.UiMessage
import ru.yofik.athena.chat.domain.model.UiMessageSenderType

// todo migrate on delegates
class MessageAdapter : ListAdapter<UiMessage, RecyclerView.ViewHolder>(UI_MESSAGE_COMPARATOR) {

    companion object {
        private const val RECEIVE_MESSAGE_TYPE = 0
        private const val SEND_MESSAGE_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            RECEIVE_MESSAGE_TYPE ->
                viewHolder =
                    ReceiveMessageViewHolder(
                        ListItemReceiveMessageBinding.inflate(inflater, parent, false)
                    )
            SEND_MESSAGE_TYPE ->
                viewHolder =
                    SendMessageViewHolder(
                        ListItemSendMessageBinding.inflate(inflater, parent, false)
                    )
            else -> {
                viewHolder =
                    ReceiveMessageViewHolder(
                        ListItemReceiveMessageBinding.inflate(inflater, parent, false)
                    )
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        when (holder.itemViewType) {
            RECEIVE_MESSAGE_TYPE -> (holder as ReceiveMessageViewHolder).bind(message)
            SEND_MESSAGE_TYPE -> (holder as SendMessageViewHolder).bind(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return when (message.senderType) {
            UiMessageSenderType.OWNER -> SEND_MESSAGE_TYPE
            UiMessageSenderType.NOT_OWNER -> RECEIVE_MESSAGE_TYPE
        }
    }

    inner class ReceiveMessageViewHolder(private val binding: ListItemReceiveMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: UiMessage) {
            binding.apply {
                time.text = message.time
                this.message.text = message.content
            }
        }
    }

    inner class SendMessageViewHolder(private val binding: ListItemSendMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: UiMessage) {
            binding.apply {
                time.text = message.time
                this.message.text = message.content
            }
        }
    }
}

private val UI_MESSAGE_COMPARATOR =
    object : DiffUtil.ItemCallback<UiMessage>() {
        override fun areItemsTheSame(oldItem: UiMessage, newItem: UiMessage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UiMessage, newItem: UiMessage): Boolean {
            return oldItem == newItem
        }
    }

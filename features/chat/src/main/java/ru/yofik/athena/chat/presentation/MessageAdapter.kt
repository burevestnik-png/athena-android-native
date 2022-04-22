package ru.yofik.athena.chat.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.yofik.athena.chat.databinding.ListItemMessageBinding
import ru.yofik.athena.chat.domain.model.UiMessage

class MessageAdapter : ListAdapter<UiMessage, MessageAdapter.ViewHolder>(UI_MESSAGE_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ListItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: UiMessage) {
            binding.apply {
                owner.text = message.sender
                time.text = message.time
                content.text = message.content
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

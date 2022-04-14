package ru.yofik.athena.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.utils.toFormattedString
import ru.yofik.athena.databinding.ListItemMessageBinding

class MessageAdapter(private val messages: List<Message>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ListItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size

    inner class ViewHolder(private val binding: ListItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.apply {
                // todo add sender name
                owner = message.senderId.toString()
                content = message.text
                time = message.date.toFormattedString()
            }
        }
    }
}
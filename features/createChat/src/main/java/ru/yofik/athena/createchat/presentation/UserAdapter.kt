package ru.yofik.athena.createchat.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.yofik.athena.createchat.databinding.ListItemUserBinding
import ru.yofik.athena.createchat.domain.model.UiUser

fun interface UserClickListener {
    fun onUserClick(id: Long, name: String)
}

class UserAdapter : ListAdapter<UiUser, UserAdapter.UserViewHolder>(UI_USER_COMPARATOR) {

    private var userClickListener: UserClickListener? = null

    fun setUserClickListener(listener: UserClickListener) {
        userClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class UserViewHolder(private val binding: ListItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UiUser) {
            binding.root.apply {
                text = user.name
                setOnClickListener { userClickListener?.onUserClick(user.id, user.name) }
            }
        }
    }
}

private val UI_USER_COMPARATOR =
    object : DiffUtil.ItemCallback<UiUser>() {
        override fun areItemsTheSame(oldItem: UiUser, newItem: UiUser): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UiUser, newItem: UiUser): Boolean {
            return oldItem == newItem
        }
    }

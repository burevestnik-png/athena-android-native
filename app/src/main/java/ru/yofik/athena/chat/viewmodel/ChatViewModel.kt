package ru.yofik.athena.chat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.domain.model.user.User

class ChatViewModel : ViewModel() {
    val messages = MutableLiveData<List<Message>>(emptyList())
    var currentMessage = MutableLiveData("")

    fun addMessage() {
        val msgs =
            mutableListOf<Message>().apply {
                messages.value?.let { addAll(it) }
                add(
                    Message(
                        id = 12,
                        text = currentMessage.value ?: "",
                        senderId = User.getYarik().id,
                        chatId = Chat.getLeshaChat().id
                    )
                )
            }

        messages.postValue(msgs)
    }
}

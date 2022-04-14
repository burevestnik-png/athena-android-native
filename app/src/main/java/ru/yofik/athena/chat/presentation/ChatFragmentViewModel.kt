package ru.yofik.athena.chat.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.domain.model.user.User

class ChatFragmentViewModel : ViewModel() {
    val messages = MutableLiveData<List<Message>>(emptyList())
    var currentMessage = MutableLiveData("")

    fun addMessage() {
        /*val msgs =
            mutableListOf<MessageWithDetails>().apply {
                messages.value?.let { addAll(it) }
                add(
                    MessageWithDetails(
                        id = 12,
                        text = currentMessage.value ?: "",
                        senderId = User.getYarik().id,
                        chatId = Chat.getLeshaChat().id
                    )
                )
            }

        messages.postValue(msgs)*/
    }
}

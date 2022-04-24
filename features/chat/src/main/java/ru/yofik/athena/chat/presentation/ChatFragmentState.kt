package ru.yofik.athena.chat.presentation

import ru.yofik.athena.common.domain.model.chat.ChatWithDetails
import ru.yofik.athena.common.presentation.FailureEvent

data class ChatFragmentState(
    val loading: Boolean = false,
    // todo probably in future it will be better to remain in state only messages, and other move to VM, because they are immutable
    val chatWithDetails: ChatWithDetails = ChatWithDetails.createNullChat(),
    val input: String = "",
    val failure: FailureEvent? = null
)

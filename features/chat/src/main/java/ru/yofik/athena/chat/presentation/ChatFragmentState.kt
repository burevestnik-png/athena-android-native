package ru.yofik.athena.chat.presentation

import ru.yofik.athena.common.domain.model.chat.ChatWithDetails
import ru.yofik.athena.common.presentation.FailureEvent

data class ChatFragmentState(
    val loading: Boolean = false,
    val chatWithDetails: ChatWithDetails = ChatWithDetails.createNullChat(),
    val failure: FailureEvent? = null
)

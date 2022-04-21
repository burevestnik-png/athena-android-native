package ru.yofik.athena.chat.presentation

import ru.yofik.athena.common.presentation.FailureEvent

data class ChatFragmentState(
    val loading: Boolean = false,
    val failure: FailureEvent? = null
)
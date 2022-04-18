package ru.yofik.athena.login.presentation

import androidx.annotation.StringRes
import ru.yofik.athena.login.R
import ru.yofik.athena.common.presentation.FailureEvent

data class LoginViewState(
    val loading: Boolean = false,
    val failure: FailureEvent? = null,
    val code: String = "",
    @StringRes val codeError: Int = R.string.no_error
)

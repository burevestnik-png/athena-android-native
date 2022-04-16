package ru.yofik.athena.common.presentation.model

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.yofik.athena.R
import ru.yofik.athena.common.presentation.FailureEvent

fun Fragment.handleFailures(event: FailureEvent?) {
    val unhandledFailure = event?.getFailureOrNull() ?: return
    val fallbackMessage = getString(R.string.an_error_occurred)

    val message =
        if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        } else {
            unhandledFailure.message!!
        }

    if (message.isNotEmpty()) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}

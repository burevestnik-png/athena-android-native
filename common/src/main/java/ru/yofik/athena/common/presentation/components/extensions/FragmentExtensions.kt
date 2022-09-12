package ru.yofik.athena.common.presentation.components.extensions

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.MenuRes
import androidx.core.net.toUri
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.yofik.athena.common.R
import ru.yofik.athena.common.presentation.model.Event

fun Fragment.handleFailures(event: Event<Throwable>?) {
    val unhandledFailure = event?.getPayloadOrNull() ?: return
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

fun Fragment.navigate(path: String) {
    val deepLink = path.toUri()
    findNavController().navigate(deepLink)
}

fun Fragment.launchViewModelsFlow(block: suspend () -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) { block() }
    }
}

val Fragment.menuHost: MenuHost
    get() = requireActivity() as MenuHost

fun Fragment.createMenuProvider(@MenuRes menuRes: Int, onMenuItemSelected: (MenuItem) -> Boolean) =
    object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(menuRes, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return onMenuItemSelected(menuItem)
        }
    }

fun Fragment.addMenuProvider(menuProvider: MenuProvider) {
    menuHost.addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
}

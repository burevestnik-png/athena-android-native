package ru.yofik.athena.common.presentation.components.extensions.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.MenuRes
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

val Fragment.menuHost: MenuHost
    get() = requireActivity()

fun createMenuProvider(@MenuRes menuRes: Int, onMenuItemSelected: (MenuItem) -> Boolean) =
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

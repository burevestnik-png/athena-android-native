package ru.yofik.athena.chatlist.presentation.contextualAppBar

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import ru.yofik.athena.chatList.R
import timber.log.Timber

class ContextualAppBar(
    private var initiallySelectedChatsAmount: Int = 0,
    private val onDeleteChats: () -> Unit,
    private val onDestroyActionMode: () -> Unit,
) : ActionMode.Callback {
    private var actionMode: ActionMode? = null

    fun updateSelectedChatsAmount(newValue: Int) {
        Timber.d("updateSelectedChatsAmount: ${actionMode == null}")
        initiallySelectedChatsAmount = newValue
        actionMode?.title = initiallySelectedChatsAmount.toString()
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        this.actionMode = mode

        mode?.menuInflater?.inflate(R.menu.select_mode_menu, menu)
        mode?.title = initiallySelectedChatsAmount.toString()
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean =
        when (item!!.itemId) {
            R.id.action_delete_chat -> {
                onDeleteChats()
                true
            }
            else -> false
        }

    override fun onDestroyActionMode(mode: ActionMode?) {
        onDestroyActionMode()
    }
}

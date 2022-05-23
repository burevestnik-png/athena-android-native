package ru.yofik.athena.common.presentation.components

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VM : ViewModel, Binding : ViewBinding>(@LayoutRes layoutId: Int) :
    Fragment(layoutId) {
    protected abstract val viewModel: VM
    protected abstract val binding: Binding

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        processInitialRequests()
    }

    protected open fun processInitialRequests() {}

    protected open fun setupUI() {}
}

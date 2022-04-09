package ru.yofik.athena.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

private const val TAG = "LoginViewModel"

class LoginViewModel : ViewModel() {
    val code = MutableLiveData("")

    fun onSubmit() {
        Timber.tag(TAG).d("ClICKERD")
    }
}

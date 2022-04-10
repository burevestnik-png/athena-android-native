package ru.yofik.athena.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import ru.yofik.athena.common.domain.repositories.UserRepository
import timber.log.Timber

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {
    val code = MutableLiveData("")
    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.d("exceptionHandler: ${throwable.message}")
    }

    fun activate() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.activateUser(code = code.value ?: "")
            Timber.d(response.toString())
        }
    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}

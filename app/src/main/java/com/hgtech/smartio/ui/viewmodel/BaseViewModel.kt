package com.hgtech.smartio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() {

    protected val baseScope: CoroutineScope =
        viewModelScope.plus(CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        })

    private var job: Job? = null
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    protected fun cancelJob() {
        job?.let {
            it.cancel()
            _isLoading.postValue(false)
        }
    }


    protected fun execute(
        postLoadingStatus: Boolean = true,
        dellay: Long = 0,
        executable: suspend () -> Unit
    ) {
        if (postLoadingStatus)
            _isLoading.postValue(true)
        job = baseScope.launch {
            delay(dellay)
            executable.invoke()
            if (postLoadingStatus)
                _isLoading.postValue(false)
            job = null
        }
    }
}
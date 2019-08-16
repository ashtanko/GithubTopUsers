package me.shtanko.topgithub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.shtanko.common.Failure
import me.shtanko.topgithub.ui.UiState
import me.shtanko.topgithub.ui.ViewState

abstract class BaseViewModel : ViewModel() {

    var failure: MutableLiveData<Failure> = MutableLiveData()
    protected lateinit var currentViewState: ViewState

    protected val _uiState: MutableLiveData<UiState> = MutableLiveData()
     val uiState: LiveData<UiState>
        get() = _uiState

    val viewState: MutableLiveData<ViewState> = MutableLiveData<ViewState>().apply {
        currentViewState = ViewState()
        value = currentViewState
    }

    protected fun handleFailure(failure: Failure) {
        this.failure.value = failure
        viewState.value = currentViewState.copy(loading = false, error = Pair(true, failure.toString()))
    }
}
package dev.shtanko.topgithub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.shtanko.common.Failure
import dev.shtanko.topgithub.ui.UiState
import dev.shtanko.topgithub.ui.ViewState

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
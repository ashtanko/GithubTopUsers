package me.shtanko.topgithub.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val disposables = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        disposables.addAll(disposable)
    }

    override fun onCleared() {
        disposables.dispose()
    }

}
package me.shtanko.topgithub.ui.main

import androidx.lifecycle.MutableLiveData
import io.reactivex.observers.DisposableObserver
import me.shtanko.domain.entity.User
import me.shtanko.domain.interactor.GetUsersUseCase
import me.shtanko.topgithub.viewmodel.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel() {

    data class ViewState(
        val loading: Boolean = true,
        val error: Pair<Boolean, String> = Pair(false, "")
    )

    private lateinit var currentViewState: ViewState

    val viewState: MutableLiveData<ViewState> = MutableLiveData<ViewState>().apply {
        currentViewState = ViewState()
        value = currentViewState
    }

    var data: MutableLiveData<List<Triple<String, String, Int>>> = MutableLiveData()

    fun loadData() {
        execute()
    }

    fun onLoadNextPage(page: Int, lastUserId: Int) {
        viewState.value = currentViewState.copy(loading = true)
        execute(page = page, since = lastUserId)
    }

    private fun execute(page: Int = 1, since: Int = 0) {
        val disposable = getUsersUseCase.execute(GetUsersUseCase.Params(page = page, since = since, perPage = 30))
            .subscribeWith(getObserver())
        addDisposable(disposable)
    }

    private fun getObserver(): DisposableObserver<List<User>> {
        return object : DisposableObserver<List<User>>() {
            override fun onComplete() {
                viewState.value = currentViewState.copy(loading = false)
            }

            override fun onNext(users: List<User>) {
                handleUsers(users)
            }

            override fun onError(e: Throwable) {
                handleFailure(e)
            }

        }
    }

    private fun handleFailure(failure: Throwable) {
        viewState.value = currentViewState.copy(loading = false, error = Pair(true, failure.localizedMessage))
    }

    private fun handleUsers(users: List<User>) {
        data.value = users.map { it -> Triple(it.avatarUrl, it.login, it.id) }
    }
}

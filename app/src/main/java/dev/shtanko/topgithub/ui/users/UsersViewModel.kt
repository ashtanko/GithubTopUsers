package dev.shtanko.topgithub.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.shtanko.common.Failure
import dev.shtanko.domain.entity.User
import dev.shtanko.domain.interactor.GetUsers
import dev.shtanko.topgithub.ui.ErrorUiState
import dev.shtanko.topgithub.ui.UiState
import dev.shtanko.topgithub.viewmodel.BaseViewModel
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val getUsers: GetUsers
) : BaseViewModel() {

    var users: MutableLiveData<List<User>> = MutableLiveData()

    private val _errorUiState: MutableLiveData<ErrorUiState> = MutableLiveData()
    val errorUiState: LiveData<ErrorUiState>
        get() = _errorUiState

    fun loadData() {
        _uiState.value = UiState.ShowProgress
        loadUsers()
    }

    fun loadNextPage(page: Int, lastUserId: Int) {
        viewState.value = currentViewState.copy(loading = true)
        loadUsers(page, lastUserId)
    }

    private fun loadUsers(page: Int = 1, since: Int = 0) {
        val params = GetUsers.Params(page = page, perPage = PER_PAGE, since = since)

        getUsers(params) {
            _uiState.value = UiState.HideProgress
            it.either(::handleError, ::handleUsers)
        }
    }

    private fun handleError(failure: Failure) {
        when (failure) {
            is Failure.NetworkConnection -> {
                _errorUiState.value = ErrorUiState.ShowNetworkConnectionError
            }
            is Failure.ServerError -> {
                _errorUiState.value = ErrorUiState.ShowServerError
            }
            is Failure.ServerException -> {
                //failure.failure
            }
        }
    }

    private fun handleUsers(users: List<User>) {
        this.users.value = users
        viewState.value = currentViewState.copy(loading = false)
    }

    companion object {
        private const val PER_PAGE = 30
    }
}
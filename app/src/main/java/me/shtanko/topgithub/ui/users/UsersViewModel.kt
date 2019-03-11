package me.shtanko.topgithub.ui.users

import androidx.lifecycle.MutableLiveData
import me.shtanko.domain.entity.User
import me.shtanko.domain.interactor.GetUsers
import me.shtanko.topgithub.viewmodel.BaseViewModel
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val getUsers: GetUsers
) : BaseViewModel() {

    var users: MutableLiveData<List<User>> = MutableLiveData()


    fun loadData() {
        loadUsers()
    }

    fun loadNextPage(page: Int, lastUserId: Int) {
        viewState.value = currentViewState.copy(loading = true)
        loadUsers(page, lastUserId)
    }

    private fun loadUsers(page: Int = 1, since: Int = 0) {
        val params = GetUsers.Params(page = page, perPage = PER_PAGE, since = since)

        getUsers(params) { it.either(::handleFailure, ::handleUsers) }
    }

    private fun handleUsers(users: List<User>) {
        this.users.value = users
        viewState.value = currentViewState.copy(loading = false)
    }

    companion object {
        private const val PER_PAGE = 30
    }
}
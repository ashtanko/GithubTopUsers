package dev.shtanko.topgithub.ui.user

import androidx.lifecycle.MutableLiveData
import dev.shtanko.domain.entity.FullUser
import dev.shtanko.domain.interactor.GetUser
import dev.shtanko.topgithub.viewmodel.BaseViewModel
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val getUser: GetUser
) : BaseViewModel() {

    var user: MutableLiveData<FullUser> = MutableLiveData()

    fun getUser(username: String) {
        val params = GetUser.Params(username)
        getUser(params) { it.either(::handleFailure, ::handleUser) }
    }

    private fun handleUser(user: FullUser) {
        this.user.value = user
    }

}
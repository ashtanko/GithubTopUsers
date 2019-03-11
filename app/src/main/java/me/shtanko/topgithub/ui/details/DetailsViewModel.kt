package me.shtanko.topgithub.ui.details

import androidx.lifecycle.MutableLiveData
import me.shtanko.domain.entity.User
import me.shtanko.topgithub.viewmodel.BaseViewModel
import javax.inject.Inject

class DetailsViewModel @Inject constructor(

) : BaseViewModel() {

    var data: MutableLiveData<User> = MutableLiveData()

    fun getUser(username: String) {

    }

    private fun handleUser(user: User) {
        data.value = user
    }

}
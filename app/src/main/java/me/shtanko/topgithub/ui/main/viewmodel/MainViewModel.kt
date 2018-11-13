package me.shtanko.topgithub.ui.main.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import me.shtanko.topgithub.ui.main.api.GithubApiService
import me.shtanko.topgithub.ui.main.entity.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val apiService: GithubApiService
) : ViewModel() {

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
        apiService.getUsers(page = page, since = since, perPage = 30).enqueue(object : Callback<List<User>> {

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                viewState.value = currentViewState.copy(loading = false)
                if (response.isSuccessful) {
                    val users = response.body()
                    val userList: MutableList<Triple<String, String, Int>> = mutableListOf()
                    users?.map {
                        val user = Triple<String, String, Int>(it.avatarUrl, it.login, it.id)
                        userList.add(user)
                    }
                    data.value = userList
                } else {
                    viewState.value = currentViewState.copy(loading = false, error = Pair(true, response.message()))
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                t.printStackTrace()
                viewState.value = currentViewState.copy(loading = false, error = Pair(true, t.localizedMessage))
            }
        })
    }
}

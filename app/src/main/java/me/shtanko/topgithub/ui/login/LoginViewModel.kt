package me.shtanko.topgithub.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.shtanko.common.Failure
import me.shtanko.common.exception.OppCodeRequiredException
import me.shtanko.domain.entity.Token
import me.shtanko.domain.interactor.ActionLogin
import me.shtanko.topgithub.ui.UiState
import me.shtanko.topgithub.utils.CoroutinesDispatcherProvider
import me.shtanko.topgithub.viewmodel.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val actionLogin: ActionLogin,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : BaseViewModel() {

    private var loginJob: Job? = null

    private val _loginUIState = MutableLiveData<LoginUiState>()
    val loginUIState: LiveData<LoginUiState>
        get() = _loginUIState

    fun login(username: String, password: String) {
        // only allow one login at a time
        if (loginJob?.isActive == true) {
            return
        }
        loginJob = performLogin(username, password)
    }

    private fun performLogin(username: String, password: String): Job {

        return viewModelScope.launch(dispatcherProvider.computation) {
            if (!isLoginValid(username, password)) {
                return@launch
            }

            withContext(dispatcherProvider.main) { showLoading() }

            val params = ActionLogin.Params(username, password)

            withContext(dispatcherProvider.main) {
                //todo
                actionLogin(params) {
                    it.either(::handleError, ::handleToken)
                }
            }
        }
    }

    private fun handleError(error: Failure) {
        when(error){
            is Failure.ServerException -> {
                when(error.failure){
                    is OppCodeRequiredException -> {
                        _loginUIState.value = LoginUiState.ShowOtpCodeDialog
                    }
                }
            }
        }
    }
    private fun showLoading() {
        _uiState.value = UiState.ShowProgress
    }

    fun loginDataChanged(username: String, password: String) {
        enableLogin(isLoginValid(username, password))
    }

    private fun enableLogin(enabled: Boolean) {
        if (enabled) {
            _loginUIState.value = LoginUiState.EnableLoginButton
        } else {
            _loginUIState.value = LoginUiState.DisableLoginButton
        }
    }

    private fun isLoginValid(username: String, password: String): Boolean {
        return username.isNotBlank() && password.isNotBlank()
    }

    private fun handleToken(token: Token) {
        Log.d("HANDLE_TOKEN", "$token")
    }

    sealed class LoginUiState {
        object EnableLoginButton : LoginUiState()
        object DisableLoginButton : LoginUiState()
        object ShowOtpCodeDialog : LoginUiState()
    }


}
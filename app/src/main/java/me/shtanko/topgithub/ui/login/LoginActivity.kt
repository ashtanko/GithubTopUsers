package me.shtanko.topgithub.ui.login

import android.app.Activity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import me.shtanko.topgithub.R
import me.shtanko.topgithub.extensions.addTextChangedListener
import me.shtanko.topgithub.platform.BaseActivity
import me.shtanko.topgithub.ui.UiState
import me.shtanko.topgithub.ui.ViewState

class LoginActivity : BaseActivity() {

    private lateinit var frame: FrameLayout
    private lateinit var username: AppCompatEditText
    private lateinit var password: AppCompatEditText
    private lateinit var login: AppCompatButton
    private lateinit var container: LinearLayout
    private lateinit var loading: ProgressBar

    private val loginViewModel: LoginViewModel by bindViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_content)

        frame = findViewById(R.id.frame)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        container = findViewById(R.id.container)
        loading = findViewById(R.id.loading)

        username.setText("adamluissean")
        password.setText("48151`Fuck62242qqAdamLuisSean")


        username.addTextChangedListener {
            loginViewModel.loginDataChanged(it.toString(), password.text.toString())
        }

        password.addTextChangedListener {
            loginViewModel.loginDataChanged(username.text.toString(), it.toString())
        }

        login.setOnClickListener {
            loginViewModel.login(username.text.toString(), password.text.toString())
        }

        frame.setOnClickListener {
            dismiss()
        }

        configureObservers()
    }

    override fun onBackPressed() {
        dismiss()
    }

    private fun configureObservers() {
        loginViewModel.loginUIState.observe(this, Observer {
            when (it) {
                is LoginViewModel.LoginUiState.EnableLoginButton -> {
                    login.isEnabled = true
                }

                is LoginViewModel.LoginUiState.DisableLoginButton -> {
                    login.isEnabled = false
                }

                is LoginViewModel.LoginUiState.ShowOtpCodeDialog -> {
                    throw RuntimeException("Show OTP Code dialog")
                }
            }
        })

        loginViewModel.uiState.observe(this, Observer {
            when (it) {
                is UiState.ShowProgress -> {
                    loading.visibility = View.VISIBLE
                }
            }
        })

        loginViewModel.viewState.observe(this, Observer<ViewState> { viewState ->
            viewState?.let {
                val error = it.error
                val isError = error.first
                val errorMessage = error.second

                if (isError) {
                    showLoginFailed(errorMessage)
                }


            }
        })

    }

    private fun beginDelayedTransition() {
        TransitionManager.beginDelayedTransition(container)
    }

    private fun showLoginFailed(errorString: String) {
        loading.visibility = View.GONE
        Snackbar.make(container, errorString, Snackbar.LENGTH_SHORT).show()
        beginDelayedTransition()
        password.requestFocus()
    }

    private fun dismiss() {
        setResult(Activity.RESULT_CANCELED)
        finishAfterTransition()
    }
}
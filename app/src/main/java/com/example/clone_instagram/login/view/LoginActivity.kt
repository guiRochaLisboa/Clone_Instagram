package com.example.clone_instagram.login.view

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.clone_instagram.R
import com.example.clone_instagram.common.util.TxtWatcher
import com.example.clone_instagram.common.base.DependencyInjector
import com.example.clone_instagram.databinding.ActivityLoginBinding
import com.example.clone_instagram.login.Login
import com.example.clone_instagram.login.presentation.LoginPresenter
import com.example.clone_instagram.main.view.MainActivity
import com.example.clone_instagram.register.view.RegisterActivity


class LoginActivity : AppCompatActivity(), Login.View {

    private lateinit var bindign: ActivityLoginBinding

    override lateinit var presenter: Login.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindign = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindign.root)

        presenter = LoginPresenter(this,DependencyInjector.loginRepository())

        with(bindign){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                when (resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        loginImgLogo.imageTintList = ColorStateList.valueOf(Color.WHITE)
                    }
                    Configuration.UI_MODE_NIGHT_NO -> {

                    }
                }
            }

            loginEditEmail.addTextChangedListener(watcher)
            loginEditEmail.addTextChangedListener(TxtWatcher{
                displayEmailFailure(null)
            })

            loginEditPassword.addTextChangedListener(watcher)
            loginEditPassword.addTextChangedListener(TxtWatcher{
                displayPasswordFailure(null)
            })

            loginBtnEnter.setOnClickListener {
            //CHAMAR O PRESENTER
            presenter.login(loginEditEmail.text.toString(),loginEditPassword.text.toString())
            }

            loginTxtRegister.setOnClickListener {
                getRegisterScreen()
            }
        }
    }

    private fun getRegisterScreen() {
        startActivity(Intent(this,RegisterActivity::class.java))
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private val watcher = TxtWatcher {
        bindign.loginBtnEnter.isEnabled = bindign.loginEditEmail.text.toString().isNotEmpty()
                && bindign.loginEditPassword.text.toString().isNotEmpty()
    }

    override fun showProgress(enabled: Boolean) {
        bindign.loginBtnEnter.showProgress(enabled)
    }

    override fun displayEmailFailure(emailError: Int?) {
        bindign.loginEditEmailInput.error = emailError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        bindign.loginEditPasswordInput.error = passwordError?.let { getString(it) }
    }

    override fun onUserAuthenticated() {
        val intent = Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onUserUnauthorized(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }
}
package com.example.clone_instagram.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import com.example.clone_instagram.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var bindign: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindign = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindign.root)

        with(bindign){
            loginEditEmail.addTextChangedListener(watcher)
            loginEditPassword.addTextChangedListener(watcher)

            loginBtnEnter.setOnClickListener {
                loginBtnEnter.showProgress(true)
                loginEditEmailInput
                    .error = "Esse e-mail é inválido"

                loginEditPasswordInput
                    .error = "Senha incorreta"

                Handler(Looper.getMainLooper()).postDelayed({
                    loginBtnEnter.showProgress(false)
                }, 2000)
            }
        }
    }


    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            bindign.loginBtnEnter.isEnabled = s.toString().isNotEmpty()
        }

        override fun afterTextChanged(p0: Editable?) {

        }
    }
}
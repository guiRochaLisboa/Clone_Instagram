package com.example.clone_instagram.login.data

import android.os.Handler
import android.os.Looper
import com.example.clone_instagram.common.model.DataBase

class FakeDataSource : LoginDataSource {


    override fun login(email: String, password: String, callback: LoginCallback) {
        Handler(Looper.getMainLooper()).postDelayed({

           val userAuth =  DataBase.usersAuth.firstOrNull{ email == it.email }

            when {
                userAuth == null -> {
                    callback.onFailure("Usuário não encontrado")
                }
                userAuth.password != password -> {
                    callback.onFailure("Senha está incorreta")
                }
                else -> {
                    DataBase.sessionAuth = userAuth
                    callback.onSuccess(userAuth)
                }
            }

            callback.onComplete()
        }, 2000)
    }
}
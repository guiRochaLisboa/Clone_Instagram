package com.example.clone_instagram.register.data

import android.os.Handler
import android.os.Looper
import com.example.clone_instagram.common.model.DataBase
import com.example.clone_instagram.common.model.UserAuth
import java.util.*

class FakeRegisterDataSource : RegisterDataSource {

    override fun create(email: String, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({

            val userAuth =  DataBase.usersAuth.firstOrNull{ email == it.email }

            if(userAuth == null){
                callback.onSuccess()
            }else {
                callback.onFailure("Usuário já cadastrado")
            }
            callback.onComplete()
        }, 2000)
    }

    override fun create(email: String, name: String, password: String, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({

            val userAuth =  DataBase.usersAuth.firstOrNull{ email == it.email }

            if(userAuth != null){
                callback.onFailure("Usuário já cadastrado")
            }else {

                val newUser = UserAuth(UUID.randomUUID().toString(),name,email,password)


                val created = DataBase.usersAuth.add(
                    UserAuth(UUID.randomUUID().toString()
                        ,name
                        ,email
                        ,password)
                )

                    if (created){
                        DataBase.sessionAuth = newUser
                        callback.onSuccess()
                    }else{
                        callback.onFailure("Erro interno no servidor")
                    }

            }
            callback.onComplete()
        }, 2000)
    }
}
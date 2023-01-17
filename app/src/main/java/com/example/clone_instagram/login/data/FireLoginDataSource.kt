package com.example.clone_instagram.login.data

import android.os.Handler
import android.os.Looper
import com.example.clone_instagram.common.model.DataBase
import com.google.firebase.auth.FirebaseAuth

class FireLoginDataSource : LoginDataSource {


    override fun login(email: String, password: String, callback: LoginCallback) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {res ->
            if(res.user != null){
             callback.onSuccess()
            }else{
                callback.onFailure("Usuário não encontrado")
            }

            }
            .addOnFailureListener { exception ->
            callback.onFailure(exception.message ?: "Erro ao fazer login")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }
}
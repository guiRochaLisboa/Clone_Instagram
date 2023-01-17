package com.example.clone_instagram.splash.data

import com.example.clone_instagram.common.model.DataBase
import com.google.firebase.auth.FirebaseAuth

class FireSplashLocalDataSource : SplashDataSource {
    override fun session(callback: SplashCallback) {
        if(FirebaseAuth.getInstance().uid != null){
            callback.onSucess()
        }else{
            callback.onFailure()
        }

    }

}
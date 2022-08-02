package com.example.clone_instagram.splash.data

import com.example.clone_instagram.common.model.DataBase

class FakeLocalDataSource : SplashDataSource {
    override fun session(callback: SplashCallback) {
        if(DataBase.sessionAuth != null){
            callback.onSucess()
        }else{
            callback.onFailure()
        }

    }

}
package com.example.clone_instagram.splash.presentation

import com.example.clone_instagram.common.base.BasePresenter
import com.example.clone_instagram.common.base.BaseView
import com.example.clone_instagram.splash.Splash
import com.example.clone_instagram.splash.data.SplashCallback
import com.example.clone_instagram.splash.data.SplashRepository

class SplashPresenter(
    private var view: Splash.View?,
    private val repository: SplashRepository
) : Splash.Presenter {
    override fun authenticated() {
    repository.session(object : SplashCallback{
        override fun onSucess() {
        view?.goToMainScreen()
        }

        override fun onFailure() {
        view?.goToLoginScreen()
        }

    })
    }

    override fun onDestroy() {
        view = null
    }
}
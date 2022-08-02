package com.example.clone_instagram.splash

import com.example.clone_instagram.common.base.BasePresenter
import com.example.clone_instagram.common.base.BaseView

interface Splash {

    interface Presenter : BasePresenter{
        fun authenticated()
    }

    interface View : BaseView<Presenter>{
        fun goToMainScreen()
        fun goToLoginScreen()
    }
}
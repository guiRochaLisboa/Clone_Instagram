package com.example.clone_instagram.login

import androidx.annotation.StringRes
import com.example.clone_instagram.common.view.base.BasePresenter
import com.example.clone_instagram.common.view.base.BaseView

interface Login {


    interface Presenter : BasePresenter{
        fun login(email: String, password: String)

    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled : Boolean)
        fun displayEmailFailure(@StringRes emailError: Int?)
        fun displayPasswordFailure(@StringRes passwordError: Int?)
        fun onUserAuthenticated()
        fun onUserUnauthorized(message: String)

    }
}
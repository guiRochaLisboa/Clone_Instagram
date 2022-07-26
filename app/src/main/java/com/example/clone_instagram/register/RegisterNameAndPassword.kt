package com.example.clone_instagram.register

import androidx.annotation.StringRes
import com.example.clone_instagram.common.view.base.BasePresenter
import com.example.clone_instagram.common.view.base.BaseView

interface RegisterNameAndPassword {

    interface Presenter : BasePresenter {
        fun create(email:String,name: String,password: String,confirm: String)

    }

    interface View: BaseView<Presenter> {
        fun showProgress(enabled : Boolean)

        fun displayNameFailure(@StringRes nameError: Int?)

        fun displayPasswordFailure(@StringRes passwordError: Int?)

        fun onCreateFailure(message: String)

        fun onCreateSucess(name: String)


    }
}
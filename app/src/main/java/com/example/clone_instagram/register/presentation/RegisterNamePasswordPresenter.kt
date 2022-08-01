package com.example.clone_instagram.register.presentation

import com.example.clone_instagram.R
import com.example.clone_instagram.register.RegisterNameAndPassword
import com.example.clone_instagram.register.data.RegisterCallback
import com.example.clone_instagram.register.data.RegisterRepository

class RegisterNamePasswordPresenter(
    private var view: RegisterNameAndPassword.View?,
    private val repository: RegisterRepository
    ) : RegisterNameAndPassword.Presenter {

    override fun create(email: String, name: String, password: String, confirm: String) {
        val isName = name.length > 3
        val isPasswordValid = password.length >= 8
        val isConfirmValid = password == confirm


        if (!isName) {
            view?.displayNameFailure(R.string.invalid_name)
        } else {
            view?.displayNameFailure(null)
        }

        if (!isPasswordValid) {
            view?.displayPasswordFailure(R.string.invalid_password)
        } else {
            if (!isConfirmValid){
                view?.displayPasswordFailure(R.string.password_not_equal)
            }else{
                view?.displayPasswordFailure(null)
            }
        }




         if (isName && isPasswordValid && isConfirmValid){
             view?.showProgress(true)

             repository.create(email,name,password,object : RegisterCallback{
                 override fun onSuccess() {
                     view?.onCreateSucess(name)
                 }

                 override fun onFailure(message: String) {
                     view?.onCreateFailure(message)
                 }

                 override fun onComplete() {
                     view?.showProgress(false)
                 }

             })
         }
    }


    override fun onDestroy() {
        view = null
    }
}
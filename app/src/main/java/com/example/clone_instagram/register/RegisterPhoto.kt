package com.example.clone_instagram.register

import android.net.Uri
import androidx.annotation.StringRes
import com.example.clone_instagram.common.base.BasePresenter
import com.example.clone_instagram.common.base.BaseView

interface RegisterPhoto {

    interface Presenter : BasePresenter {
        fun updateUser(photoUri: Uri)
    }

    interface View: BaseView<Presenter> {
        fun showProgress(enabled : Boolean)

        fun onUpdateFailure(message: String)

        fun onUpdateSucess()


    }
}
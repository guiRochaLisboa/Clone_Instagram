package com.example.clone_instagram.post

import android.net.Uri
import com.example.clone_instagram.common.base.BasePresenter
import com.example.clone_instagram.common.base.BaseView


interface Post {

    interface Presenter : BasePresenter {
        fun fetchPictures()
        fun selectUri(uri: Uri)
        fun getSelectedUri() : Uri?

    }

    interface View : BaseView<Presenter>{
        fun showProgress(enabled: Boolean)
        fun displayFullPictures(post: List<Uri>)
        fun displayEmptyPictures()
        fun displayRequestFailure(message: String)
    }
}
package com.example.clone_instagram.profile

import com.example.clone_instagram.common.base.BasePresenter
import com.example.clone_instagram.common.base.BaseView
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth

interface Profile {

    interface Presenter : BasePresenter {
        fun fetchUserProfile()
        fun fetchUserPost()
    }


    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayUserProfile(userAuth: UserAuth)
        fun displayRequestFailure(message: String)
        fun displayEmptyPost()
        fun displayFullPost(post: List<Post>)
    }

}
package com.example.clone_instagram.profile

import com.example.clone_instagram.common.base.BasePresenter
import com.example.clone_instagram.common.base.BaseView
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth

interface Profile {

    interface Presenter : BasePresenter {
        fun fetchUserProfile(uuid : String?)
        fun fetchUserPost(uuid : String?)
        fun followUser(uuid: String?,follow: Boolean)
        fun clear()
    }


    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayUserProfile(user: Pair<UserAuth,Boolean?>)
        fun displayRequestFailure(message: String)
        fun displayEmptyPost()
        fun displayFullPost(post: List<Post>)
    }

}
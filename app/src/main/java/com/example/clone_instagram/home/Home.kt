package com.example.clone_instagram.home

import com.example.clone_instagram.common.base.BasePresenter
import com.example.clone_instagram.common.base.BaseView
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth

interface Home {

    interface Presenter : BasePresenter{
        fun fetchFeed()
        fun logout()
        fun clear()
    }

    interface View : BaseView<Presenter>{
        fun showProgress(enabled: Boolean)
        fun displayRequestFailure(message: String)
        fun displayEmptyPost()
        fun displayFullPost(post: List<Post>)
    }
}
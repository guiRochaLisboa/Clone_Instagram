package com.example.clone_instagram.search

import com.example.clone_instagram.common.base.BasePresenter
import com.example.clone_instagram.common.base.BaseView
import com.example.clone_instagram.common.model.User
import com.example.clone_instagram.common.model.UserAuth

interface Search {

    interface Presenter : BasePresenter {
        fun fetchUsers(name : String)
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled : Boolean)
        fun displayFullUser(user: List<User>)
        fun displayEmptyUsers()
    }

}
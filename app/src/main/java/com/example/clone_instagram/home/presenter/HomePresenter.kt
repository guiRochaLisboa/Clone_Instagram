package com.example.clone_instagram.home.presenter

import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth
import com.example.clone_instagram.home.Home
import com.example.clone_instagram.home.data.HomeRepository

class HomePresenter (
    private var view: Home.View?,
    private val repository: HomeRepository
) : Home.Presenter {


    override fun fetchFeed() {
        view?.showProgress(true)
        repository.fetchFeed( object : RequestCallback<List<Post>> {
            override fun onSucess(data: List<Post>) {
                if (data.isEmpty()) {
                    view?.displayEmptyPost()
                } else {
                    view?.displayFullPost(data)
                }
            }

            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }

            override fun onComplete() {
                view?.showProgress(false)
            }

        })
    }

    override fun logout() {
        repository.logout()
    }

    override fun clear() {
        repository.clearCache()
    }


    override fun onDestroy() {
        view = null
    }
}
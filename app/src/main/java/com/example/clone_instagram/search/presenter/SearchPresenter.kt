package com.example.clone_instagram.search.presenter

import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.DataBase
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.User
import com.example.clone_instagram.common.model.UserAuth
import com.example.clone_instagram.profile.Profile
import com.example.clone_instagram.profile.data.ProfileRepository
import com.example.clone_instagram.profile.view.ProfileFragment
import com.example.clone_instagram.register.RegisterEmail
import com.example.clone_instagram.register.data.RegisterCallback
import com.example.clone_instagram.register.data.RegisterRepository
import com.example.clone_instagram.search.Search
import com.example.clone_instagram.search.data.SearchRepository
import java.lang.RuntimeException

class SearchPresenter(
    private var view: Search.View?,
    private val repository: SearchRepository
) : Search.Presenter {


    override fun fetchUsers(name: String) {
        view?.showProgress(true)
        repository.fetchUsers(name,object : RequestCallback<List<User>> {
            override fun onSucess(data: List<User>) {
               if(data.isEmpty()){
                   view?.displayEmptyUsers()
               }else{
                   view?.displayFullUser(data)
               }
            }

            override fun onFailure(message : String) {
                view?.displayEmptyUsers()
            }

            override fun onComplete() {
            view?.showProgress(false)
            }

        })
    }


    override fun onDestroy() {
        view = null
    }
}
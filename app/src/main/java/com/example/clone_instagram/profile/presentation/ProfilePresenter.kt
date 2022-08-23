package com.example.clone_instagram.profile.presentation

import android.provider.ContactsContract
import android.util.Patterns
import com.example.clone_instagram.R
import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.DataBase
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth
import com.example.clone_instagram.profile.Profile
import com.example.clone_instagram.profile.data.ProfileRepository
import com.example.clone_instagram.profile.view.ProfileFragment
import com.example.clone_instagram.register.RegisterEmail
import com.example.clone_instagram.register.data.RegisterCallback
import com.example.clone_instagram.register.data.RegisterRepository
import java.lang.RuntimeException

class ProfilePresenter(
    private var view: Profile.View?,
    private val repository: ProfileRepository
) : Profile.Presenter {



    override fun fetchUserProfile() {
        view?.showProgress(true)
        repository.fetchUserProfile(object : RequestCallback<UserAuth> {
            override fun onSucess(data: UserAuth) {
                view?.displayUserProfile(data)
            }

            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }

            override fun onComplete() {

            }

        })
    }

    override fun fetchUserPost() {
        repository.fetchUserPosts( object : RequestCallback<List<Post>> {
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


    override fun onDestroy() {
        view = null
    }
}
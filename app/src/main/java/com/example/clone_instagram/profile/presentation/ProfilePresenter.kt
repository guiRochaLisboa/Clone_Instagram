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

    var user: UserAuth? = null
    var posts: List<Post>? = null


    override fun subscribe(state: Profile.State?) {
        posts = state?.fetchUserPost()

        if (posts != null) {
            if (posts!!.isEmpty()) {
                view?.displayEmptyPost()
            } else {
                view?.displayFullPost(posts!!)
            }
        } else {
            val userUUID = DataBase.sessionAuth?.uuid ?: throw RuntimeException("user not found")
            repository.fetchUserPosts(userUUID, object : RequestCallback<List<Post>> {
                override fun onSucess(data: List<Post>) {
                    posts = data
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

        user = state?.fetchUserProfile()
        if (user != null) {
            view?.displayUserProfile(user!!)
        }
        else{
            view?.showProgress(true)
        val userUUID = DataBase.sessionAuth?.uuid ?: throw RuntimeException("user not found")
        repository.fetchUserProfile(userUUID, object : RequestCallback<UserAuth> {
            override fun onSucess(data: UserAuth) {
                user = data
                view?.displayUserProfile(data)
            }

            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }

            override fun onComplete() {

            }

        })
        }
    }

    override fun getState(): Profile.State {
        return ProfileState(posts, user)
    }

//    override fun fetchUserProfile() {
//        view?.showProgress(true)
//        val userUUID = DataBase.sessionAuth?.uuid ?: throw RuntimeException("user not found")
//        repository.fetchUserProfile(userUUID, object : RequestCallback<UserAuth> {
//            override fun onSucess(data: UserAuth) {
//                state = data
//                view?.displayUserProfile(data)
//            }
//
//            override fun onFailure(message: String) {
//                view?.displayRequestFailure(message)
//            }
//
//            override fun onComplete() {
//
//            }
//
//        })
//    }

//    override fun fetchUserPost() {
//        val userUUID = DataBase.sessionAuth?.uuid ?: throw RuntimeException("user not found")
//        repository.fetchUserPosts(userUUID, object : RequestCallback<List<Post>> {
//            override fun onSucess(data: List<Post>) {
//                if (data.isEmpty()) {
//                    view?.displayEmptyPost()
//                } else {
//                    view?.displayFullPost(data)
//                }
//            }
//
//            override fun onFailure(message: String) {
//                view?.displayRequestFailure(message)
//            }
//
//            override fun onComplete() {
//                view?.showProgress(false)
//            }
//
//        })
//    }


    override fun onDestroy() {
        view = null
    }
}
package com.example.clone_instagram.profile.data

import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth

class ProfileRepository(private val dataSourceFactory: ProfileDataSourceFactory) {

    fun fetchUserProfile(callback: RequestCallback<UserAuth>){
        val localDataSource = dataSourceFactory.createLocalDataSource()
        val userAuth = localDataSource.fetchSession()

        val dataSource = dataSourceFactory.createFromUser()

        dataSource.fetchUserProfile(userAuth.uuid,object : RequestCallback<UserAuth>{
            override fun onSucess(data: UserAuth) {
                localDataSource.putUser(data)
                callback.onSucess(data)
            }

            override fun onFailure(message: String) {

            }

            override fun onComplete() {

            }

        })
    }

    fun fetchUserPosts(callback: RequestCallback<List<Post>>){
        val localDataSource = dataSourceFactory.createLocalDataSource()
        val userAuth = localDataSource.fetchSession()

        val dataSource = dataSourceFactory.createFromPosts()
    dataSource.fetchUserPost(userAuth.uuid,object : RequestCallback<List<Post>>{
        override fun onSucess(data: List<Post>) {
        localDataSource.putPosts(data)
        callback.onSucess(data)
        }

        override fun onFailure(message: String) {
            callback.onFailure("")
        }

        override fun onComplete() {
            callback.onComplete()
        }

    })
    }
}
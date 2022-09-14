package com.example.clone_instagram.home.data

import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth

class HomeRepository(private val dataSourceFactory: HomeDataSourceFactory) {


    fun fetchFeed(callback: RequestCallback<List<Post>>){
        val localDataSource = dataSourceFactory.createLocalDataSource()
        val userAuth = localDataSource.fetchSession()

        val dataSource = dataSourceFactory.createFromFeed()
    dataSource.fetchFeed(userAuth.uuid,object : RequestCallback<List<Post>>{
        override fun onSucess(data: List<Post>) {
        localDataSource.putFeed(data)
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
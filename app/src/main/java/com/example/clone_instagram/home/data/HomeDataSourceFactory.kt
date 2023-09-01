package com.example.clone_instagram.home.data

import com.example.clone_instagram.common.base.Cache
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth

class HomeDataSourceFactory(
    private val feedCache: Cache<List<Post>>

){
    fun createLocalDataSource() : HomeDataSource{
        return HomeLocalDataSource(feedCache)
    }

    fun createRemoteDataSource() : HomeDataSource{
        return FireHomeDataSource()
    }

    fun createFromFeed() : HomeDataSource{
        return if(feedCache.isCache()){
            HomeLocalDataSource(feedCache)
        }else{
            FireHomeDataSource()
        }
    }

}
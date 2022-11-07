package com.example.clone_instagram.profile.data

import com.example.clone_instagram.common.base.Cache
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth

class ProfileDataSourceFactory(
    private val profileCache: Cache<UserAuth>,
    private val postsCache: Cache<List<Post>>

){
    fun createLocalDataSource() : ProfileDataSource{
        return ProfileLocalDataSource(profileCache,postsCache)
    }

    fun createFromUser(): ProfileDataSource{
        return if(profileCache.isCache()){
            ProfileLocalDataSource(profileCache,postsCache)
        }else{
            ProfileFakeRemoteDataSource()
        }
    }

    fun createFromPosts() : ProfileDataSource{
        return if(postsCache.isCache()){
            ProfileLocalDataSource(profileCache,postsCache)
        }else{
            ProfileFakeRemoteDataSource()
        }
    }

}
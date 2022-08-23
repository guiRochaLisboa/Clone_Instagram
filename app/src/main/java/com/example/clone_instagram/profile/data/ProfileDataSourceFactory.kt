package com.example.clone_instagram.profile.data

import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth

class ProfileDataSourceFactory(
    private val profileCache: ProfileCache<UserAuth>,
    private val postsCache: ProfileCache<List<Post>>

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
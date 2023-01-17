package com.example.clone_instagram.profile.data

import com.example.clone_instagram.common.base.Cache
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.User
import com.example.clone_instagram.common.model.UserAuth
import java.util.*

class ProfileDataSourceFactory(
    private val profileCache: Cache<Pair<User,Boolean?>>,
    private val postsCache: Cache<List<Post>>

){
    fun createLocalDataSource() : ProfileDataSource{
        return ProfileLocalDataSource(profileCache,postsCache)
    }

    fun createRemoteDataSource() : ProfileDataSource{
        return FireProfileDataSource( )
    }

    fun createFromUser(uuid: String?): ProfileDataSource{
        if(uuid != null){
            return createRemoteDataSource()
        }
        if(profileCache.isCache()){
            ProfileLocalDataSource(profileCache,postsCache)
        }
        return createRemoteDataSource()
    }

    fun createFromPosts(uuid: String?) : ProfileDataSource{
        if(uuid != null){
            return createRemoteDataSource()
        }
        if(postsCache.isCache()){
            ProfileLocalDataSource(profileCache,postsCache)
        }
         return   createRemoteDataSource()
    }

}
package com.example.clone_instagram.profile.data

import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth

class ProfileRepository(private val dataSource: ProfileDataSource) {

    fun fetchUserProfile(userUUID: String,callback: RequestCallback<UserAuth>){
        dataSource.fetchUserProfile(userUUID,callback)
    }

    fun fetchUserPosts(userUUID: String,callback: RequestCallback<List<Post>>){
    dataSource.fetchUserPost(userUUID,callback)
    }
}
package com.example.clone_instagram.profile.data

import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth

interface ProfileDataSource {
    fun fetchUserProfile(userUUID: String,callback: RequestCallback<UserAuth>)

    fun fetchUserPost(userUUID: String,callback: RequestCallback<List<Post>>)
}
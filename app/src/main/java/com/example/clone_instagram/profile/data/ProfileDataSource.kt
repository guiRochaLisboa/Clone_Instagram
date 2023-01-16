package com.example.clone_instagram.profile.data

import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth
import java.lang.UnsupportedOperationException
import java.net.CacheResponse

interface ProfileDataSource {
    fun fetchUserProfile(userUUID: String,callback: RequestCallback<Pair<UserAuth,Boolean?>>)

    fun fetchUserPost(userUUID: String,callback: RequestCallback<List<Post>>)

    fun fetchSession() : UserAuth{ throw UnsupportedOperationException()} //Método não suportado se não for subscrito

    fun followUser(userUUID: String,isFollow: Boolean,callback: RequestCallback<Boolean>) { throw UnsupportedOperationException()}

    fun putUser(response: Pair<UserAuth,Boolean?>){ throw UnsupportedOperationException()} //Método não suportado se não for subscrito

    fun putPosts(response: List<Post>?){ throw UnsupportedOperationException()} //Método não suportado se não for subscrito

}
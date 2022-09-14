package com.example.clone_instagram.home.data

import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth
import java.lang.UnsupportedOperationException
import java.net.CacheResponse

interface HomeDataSource {

    fun fetchFeed(userUUID: String,callback: RequestCallback<List<Post>>)

    fun fetchSession() : UserAuth{ throw UnsupportedOperationException()} //Método não suportado se não for subscrito

    fun putFeed(response: List<Post>){ throw UnsupportedOperationException()} //Método não suportado se não for subscrito

}
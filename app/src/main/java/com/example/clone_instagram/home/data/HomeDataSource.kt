package com.example.clone_instagram.home.data

import android.media.UnsupportedSchemeException
import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth
import java.net.CacheResponse
import kotlin.UnsupportedOperationException

interface HomeDataSource {

    fun fetchFeed(userUUID: String,callback: RequestCallback<List<Post>>)

    fun fetchSession() : String{ throw UnsupportedOperationException()} //Método não suportado se não for subscrito

    fun putFeed(response: List<Post>?){ throw UnsupportedOperationException()} //Método não suportado se não for subscrito

    fun logout() {throw UnsupportedOperationException()}

}
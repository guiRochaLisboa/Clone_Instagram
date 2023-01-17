package com.example.clone_instagram.home.data

import com.example.clone_instagram.common.base.Cache
import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.DataBase
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth
import com.google.firebase.auth.FirebaseAuth
import java.lang.RuntimeException

class HomeLocalDataSource(
    private val feedCache : Cache<List<Post>>?
) : HomeDataSource {


    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        val posts = feedCache?.get(userUUID)
        if (posts != null){
            callback.onSucess(posts)
        }else{
            callback.onFailure("posts não existem")
        }
        callback.onComplete()
    }

    override fun putFeed(response: List<Post>?) {
       feedCache?.put(response)
    }


    override fun fetchSession(): String {
       return FirebaseAuth.getInstance().uid ?: throw RuntimeException("usuário não logado")
    }

}
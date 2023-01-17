package com.example.clone_instagram.profile.data

import com.example.clone_instagram.common.base.Cache
import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.DataBase
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.User
import com.example.clone_instagram.common.model.UserAuth
import com.google.firebase.auth.FirebaseAuth
import java.lang.RuntimeException

class ProfileLocalDataSource(
    private val profileCache : Cache<Pair<User,Boolean?>>,
    private val posstCache : Cache<List<Post>>
) : ProfileDataSource {
    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<User,Boolean?>>) {
       val userAuth = profileCache.get(userUUID)
        if (userAuth != null){
            callback.onSucess(userAuth)
        }else{
            callback.onFailure("Usuário não encontrado")
        }
        callback.onComplete()
    }

    override fun fetchUserPost(userUUID: String, callback: RequestCallback<List<Post>>) {
       val posts = posstCache.get(userUUID)
        if (posts != null){
        callback.onSucess(posts)
        }else{
            callback.onFailure("posts não existem")
        }
        callback.onComplete()
    }

    override fun fetchSession(): String {
       return FirebaseAuth.getInstance().uid ?: throw RuntimeException("usuário não logado")
    }

    override fun putUser(response: Pair<User,Boolean?>) {
        profileCache.put(response)
    }

    override fun putPosts(response: List<Post>?) {
        posstCache.put(response)
    }
}
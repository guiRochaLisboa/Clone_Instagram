package com.example.clone_instagram.profile.data

import com.example.clone_instagram.common.base.Cache
import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.DataBase
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth
import java.lang.RuntimeException

class ProfileLocalDataSource(
    private val profileCache : Cache<Pair<UserAuth,Boolean?>>,
    private val posstCache : Cache<List<Post>>
) : ProfileDataSource {
    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<UserAuth,Boolean?>>) {
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

    override fun fetchSession(): UserAuth {
       return DataBase.sessionAuth ?: throw RuntimeException("usuário não logado")
    }

    override fun putUser(response: Pair<UserAuth,Boolean?>) {
        profileCache.put(response)
    }

    override fun putPosts(response: List<Post>?) {
        posstCache.put(response)
    }
}
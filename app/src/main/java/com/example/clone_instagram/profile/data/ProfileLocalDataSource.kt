package com.example.clone_instagram.profile.data

import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.DataBase
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth
import java.lang.RuntimeException

class ProfileLocalDataSource(
    private val profileCache : ProfileCache<UserAuth>,
    private val posstCache : ProfileCache<List<Post>>
) : ProfileDataSource {
    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<UserAuth>) {
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

    override fun putUser(response: UserAuth) {
        profileCache.put(response)
    }

    override fun putPosts(response: List<Post>) {
        posstCache.put(response)
    }
}
package com.example.clone_instagram.profile.data

import android.os.Handler
import android.os.Looper
import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.DataBase
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth

class ProfileFakeRemoteDataSource : ProfileDataSource {
    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<UserAuth>) {
        Handler(Looper.getMainLooper()).postDelayed({

            val userAuth = DataBase.usersAuth.firstOrNull { userUUID == it.uuid }

            if (userAuth != null) {
                callback.onSucess(userAuth)
            } else {
                callback.onFailure("Usuário não encontrado")
            }
            callback.onComplete()
        }, 2000)

    }

    override fun fetchUserPost(userUUID: String, callback: RequestCallback<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({

            val posts = DataBase.posts[userUUID]

            callback.onSucess(posts?.toList() ?: emptyList())

            callback.onComplete()
        }, 2000)

    }

}
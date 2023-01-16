package com.example.clone_instagram.profile.data

import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.DataBase
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth

class ProfileFakeRemoteDataSource : ProfileDataSource {
    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<UserAuth,Boolean?>>) {
        Handler(Looper.getMainLooper()).postDelayed({

            val userAuth = DataBase.usersAuth.firstOrNull { userUUID == it.uuid }

            if (userAuth != null) {
                if(userAuth == DataBase.sessionAuth){
                    callback.onSucess(Pair(userAuth,null))
                }else{
                    val following = DataBase.followers[DataBase.sessionAuth!!.uuid]
                    val destUser = following?.firstOrNull { it == userUUID }

                    callback.onSucess(Pair(userAuth,destUser != null))
                }
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

    override fun followUser(userUUID: String,isFollow: Boolean,callback: RequestCallback<Boolean>) {
        Handler(Looper.getMainLooper()).postDelayed({
            var followers = DataBase.followers[DataBase.sessionAuth!!.uuid]

            if(followers == null){
                followers = mutableSetOf()
                DataBase.followers[DataBase.sessionAuth!!.uuid] = followers
            }

            if(isFollow){
                DataBase.followers[DataBase.sessionAuth!!.uuid]!!.add(userUUID)
            }else{
                DataBase.followers[DataBase.sessionAuth!!.uuid]!!.remove(userUUID)
            }

            callback.onSucess(true)
            callback.onComplete()
        },500)
    }

}
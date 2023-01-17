package com.example.clone_instagram.add.data

import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.DataBase
import com.example.clone_instagram.common.model.Post
import java.util.*

class AddFakeRemoteDataSource : AddDataSource {


    override fun createPost(
        userUUID: String,
        uri: Uri,
        caption: String,
        callback: RequestCallback<Boolean>
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            var posts = DataBase.posts[userUUID]

            if(posts == null){
                posts = mutableSetOf()
                DataBase.posts[userUUID] = posts
            }

            //TODO: Remover essa classe
            val post = Post(UUID.randomUUID().toString(),null,caption,System.currentTimeMillis(),null)

            posts.add(post)

            var followers = DataBase.followers[userUUID]

            if(followers == null){
                followers = mutableSetOf()
                DataBase.followers[userUUID] = followers
            }else{
                for(follower in followers){
                    DataBase.feeds[follower]?.add(post)
                }

                DataBase.feeds[userUUID]?.add(post)
            }

            callback.onSucess(true)
        },1000)
    }

}
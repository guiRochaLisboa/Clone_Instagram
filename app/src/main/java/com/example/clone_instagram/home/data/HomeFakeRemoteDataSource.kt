package com.example.clone_instagram.home.data

import android.os.Handler
import android.os.Looper
import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.DataBase
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth

class HomeFakeRemoteDataSource : HomeDataSource {

    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({

            val feed = DataBase.feeds[userUUID]

            callback.onSucess(feed?.toList() ?: emptyList())

            callback.onComplete()
        }, 2000)
    }

}
package com.example.clone_instagram.search.data

import android.os.Handler
import android.os.Looper
import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.DataBase
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth

class SearchFakeRemoteDataSource : SearchDataSource {

    override fun fetchUsers(name: String, callback: RequestCallback<List<UserAuth>>) {
        Handler(Looper.getMainLooper()).postDelayed({
                val users = DataBase.usersAuth.filter {
                    it.name.lowercase().startsWith(name.lowercase()) && it.uuid != DataBase.sessionAuth!!.uuid
                }
            callback.onSucess(users.toList())

            callback.onComplete()
        }, 2000)

    }

}
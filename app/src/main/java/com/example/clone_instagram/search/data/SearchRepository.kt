package com.example.clone_instagram.search.data

import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.User
import com.example.clone_instagram.common.model.UserAuth

class SearchRepository(private val dataSource : SearchDataSource) {


    fun fetchUsers(name: String, callback: RequestCallback<List<User>>){
        dataSource.fetchUsers(name,object : RequestCallback<List<User>>{
            override fun onSucess(data: List<User>) {
                callback.onSucess(data)
            }

            override fun onFailure(message: String) {
            callback.onFailure(message)
            }

            override fun onComplete() {
            callback.onComplete()
            }

        })
    }


}
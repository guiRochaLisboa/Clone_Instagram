package com.example.clone_instagram.search.data

import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.UserAuth

interface SearchDataSource {
    fun fetchUsers(name: String, callback : RequestCallback<List<UserAuth>>)
}
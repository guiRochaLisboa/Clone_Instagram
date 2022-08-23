package com.example.clone_instagram.profile.data

import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth

object PostsListMemoryCache : ProfileCache<List<Post>>{

    private var posts: List<Post>? = null


    override fun isCache(): Boolean {
        return posts != null
    }

    override fun get(key: String): List<Post>? {
        return posts
    }

    override fun put(data: List<Post>) {
        posts = data
    }
}
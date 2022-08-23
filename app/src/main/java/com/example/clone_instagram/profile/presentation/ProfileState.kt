package com.example.clone_instagram.profile.presentation

import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth
import com.example.clone_instagram.profile.Profile

class ProfileState(
    private val posts: List<Post>?,
    private val userAuth: UserAuth?
) : Profile.State {
    override fun fetchUserProfile(): UserAuth? {
        return userAuth
    }

    override fun fetchUserPost(): List<Post>? {
        return posts
    }
}
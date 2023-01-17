package com.example.clone_instagram.login.data

import com.example.clone_instagram.common.model.UserAuth

interface LoginCallback {
    fun onSuccess()
    fun onFailure(message : String)
    fun onComplete()
}
package com.example.clone_instagram.register.data

import com.example.clone_instagram.common.model.UserAuth

interface RegisterCallback {
    fun onSuccess()
    fun onFailure(message : String)
    fun onComplete()
}
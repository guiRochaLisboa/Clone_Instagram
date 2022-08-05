package com.example.clone_instagram.common.base

interface RequestCallback<T> {
    fun onSucess(data: T)
    fun onFailure(message: String)
    fun onComplete()
}
package com.example.clone_instagram.common.base

interface Cache<T> {

    fun isCache() : Boolean
    fun get(key: String) : T?
    fun put(data : T)
}
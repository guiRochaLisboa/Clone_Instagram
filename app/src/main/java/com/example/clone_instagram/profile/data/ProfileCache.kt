package com.example.clone_instagram.profile.data

interface ProfileCache<T> {

    fun isCache() : Boolean
    fun get(key: String) : T?
    fun put(data : T)
}
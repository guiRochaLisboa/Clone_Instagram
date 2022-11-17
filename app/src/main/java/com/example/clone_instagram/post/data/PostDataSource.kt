package com.example.clone_instagram.post.data

import android.net.Uri

interface PostDataSource {
    suspend fun fectchPictures() : List<Uri>
}
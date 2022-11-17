package com.example.clone_instagram.post.data

import android.net.Uri

class PostRepository(private val dataSource: PostDataSource) {

    suspend fun fetchPictures() : List<Uri> = dataSource.fectchPictures()

}
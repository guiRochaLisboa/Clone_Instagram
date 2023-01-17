package com.example.clone_instagram.home.data

import android.os.Handler
import android.os.Looper
import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.DataBase
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import java.lang.RuntimeException

class FireHomeDataSource : HomeDataSource {

    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        val uid = FirebaseAuth.getInstance().uid ?: throw RuntimeException("Usuário não encontrado")
        FirebaseFirestore.getInstance()
            .collection("/feeds") // [varios feeds]
            .document(uid) // [um usuário (corrente)]
            .collection("posts")// [posts dos outros usuários]
            .orderBy("timestamp",Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {res ->
            val feed = mutableListOf<Post>()
                val documents = res.documents
                for (document in documents ){
                    val post = document.toObject(Post::class.java)
                    post?.let { feed.add(it) }
                }
                callback.onSucess(feed)
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Erro ao carregar o feed")
            }
            .addOnCompleteListener { callback.onComplete() }

    }

}
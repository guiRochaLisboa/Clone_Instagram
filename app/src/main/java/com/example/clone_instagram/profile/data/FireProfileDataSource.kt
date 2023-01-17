package com.example.clone_instagram.profile.data

import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.User
import com.example.clone_instagram.common.model.UserAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FireProfileDataSource : ProfileDataSource {

    override fun fetchUserProfile(
        userUUID: String,
        callback: RequestCallback<Pair<User, Boolean?>>
    ) {
            FirebaseFirestore.getInstance()
                .collection("/users")
                .document(userUUID)
                .get()
                .addOnSuccessListener {res ->
                val user = res.toObject(User::class.java)

                    when(user){
                        null -> {
                            callback.onFailure("Falha ao converter usuário")
                        }
                        else -> {
                         if(user.uuid == FirebaseAuth.getInstance().uid){
                             callback.onSucess(Pair(user,null))
                         }else{
                             FirebaseFirestore.getInstance()
                                 .collection("/followers")
                                 .document(FirebaseAuth.getInstance().uid!!)
                                 .collection("followers")
                                 .whereEqualTo("uuid",userUUID)
                                 .get()
                                 .addOnSuccessListener { res ->
                                     callback.onSucess(Pair(user,! res.isEmpty))
                                 }
                                 .addOnFailureListener { exception -> callback.onFailure(exception.message ?: "Falha ao buscar seguidores") }
                                 .addOnCompleteListener { callback.onComplete() }
                         }
                        }
                    }
                }
                .addOnFailureListener { exception -> callback.onFailure(exception.message ?: "Falha ao buscar usuário") }
    }

    override fun fetchUserPost(userUUID: String, callback: RequestCallback<List<Post>>) {
        FirebaseFirestore.getInstance()
            .collection("posts")
            .document(userUUID)
            .collection("posts")
            .orderBy("timestamp",Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { res ->
                val documents = res.documents
                val posts = mutableListOf<Post>()
                for (document in documents){
                    val post = document.toObject(Post::class.java)
                    post?.let {
                        posts.add(it)
                    }
                }
                callback.onSucess(posts)
            }
            .addOnFailureListener { exception -> callback.onFailure(exception.message ?: "Falha ao buscar posts") }
            .addOnCompleteListener { callback.onComplete() }
    }

    override fun followUser(
        userUUID: String,
        isFollow: Boolean,
        callback: RequestCallback<Boolean>
    ) {

    }
}
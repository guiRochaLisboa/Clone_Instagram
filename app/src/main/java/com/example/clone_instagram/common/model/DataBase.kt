package com.example.clone_instagram.common.model

import android.net.Uri
import android.provider.ContactsContract
import java.util.*

/**
 * Banco de dados fake para realizar teste sem a necessidade de uma API ou dados externos
 */

object DataBase {

    val usersAuth = mutableListOf<UserAuth>() /**O hashSetOf permite que utilizamos o userAuth com indentificador único é uma lista de usuarios gravadas temporariamente*/
    val posts = hashMapOf<String,MutableSet<Post>>()
    val feeds = hashMapOf<String,MutableSet<Post>>()
    val followers = hashMapOf<String,Set<String>>()

    /**
     * Estrutura do posts
     * "UserA" : ["posts1","posts2"],
     * "UserB" : ["posts1","posts2","posts3"]
     */

    var sessionAuth : UserAuth? = null /**Referencia da sessão do usuario atual*/

    init {
        val userA = UserAuth(UUID.randomUUID().toString(),"UserA","userA@gmail.com","12345678",null)
        val userB = UserAuth(UUID.randomUUID().toString(),"UserB","userB@gmail.com","87654321",null)
        usersAuth.add(userA)
        usersAuth.add(userB)

        followers[userA.uuid] = hashSetOf()
        posts[userA.uuid] = hashSetOf()
        feeds[userA.uuid] = hashSetOf()

        followers[userB.uuid] = hashSetOf()
        posts[userB.uuid] = hashSetOf()
        feeds[userB.uuid] = hashSetOf()

        /*feeds[userA.uuid]?.addAll(
            arrayListOf(
                Post(UUID.randomUUID().toString(), Uri.fromFile(""))
            )
        )*/


        sessionAuth = usersAuth.first()
    }

}
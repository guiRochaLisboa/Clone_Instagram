package com.example.clone_instagram.common.model

import android.provider.ContactsContract
import java.util.*

/**
 * Banco de dados fake para realizar teste sem a necessidade de uma API ou dados externos
 */

object DataBase {

    val usersAuth = hashSetOf<UserAuth>() /**O hashSetOf permite que utilizamos o userAuth com indentificador único é uma lista de usuarios gravadas temporariamente*/
    val photos = hashSetOf<Photo>()
    val posts = hashMapOf<String,Set<Post>>()

    /**
     * Estrutura do posts
     * "UserA" : ["posts1","posts2"],
     * "UserB" : ["posts1","posts2","posts3"]
     */

    var sessionAuth : UserAuth? = null /**Referencia da sessão do usuario atual*/

    init {
        usersAuth.add(UserAuth(UUID.randomUUID().toString(),"UserA","userA@gmail.com","12345678"))
        usersAuth.add(UserAuth(UUID.randomUUID().toString(),"UserB","userB@gmail.com","87654321"))

        sessionAuth = usersAuth.first()
    }

}
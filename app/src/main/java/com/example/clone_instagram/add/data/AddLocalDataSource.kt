package com.example.clone_instagram.add.data

import com.example.clone_instagram.common.model.DataBase
import com.example.clone_instagram.common.model.UserAuth
import java.lang.RuntimeException

class AddLocalDataSource : AddDataSource {

    override fun fetchSession(): UserAuth {
        return DataBase.sessionAuth ?: throw RuntimeException("Usuário não logado!!")
    }
}
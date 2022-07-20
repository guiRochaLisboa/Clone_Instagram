package com.example.clone_instagram.common.view.base

import com.example.clone_instagram.login.data.FakeDataSource
import com.example.clone_instagram.login.data.LoginRepository

object DependencyInjector {
    fun loginRepository() : LoginRepository{
        return LoginRepository(FakeDataSource())
    }
}
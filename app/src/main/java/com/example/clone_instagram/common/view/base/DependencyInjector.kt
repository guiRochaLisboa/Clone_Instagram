package com.example.clone_instagram.common.view.base

import com.example.clone_instagram.login.data.FakeDataSource
import com.example.clone_instagram.login.data.LoginRepository
import com.example.clone_instagram.register.data.FakeRegisterDataSource
import com.example.clone_instagram.register.data.RegisterRepository

object DependencyInjector {
    fun loginRepository() : LoginRepository{
        return LoginRepository(FakeDataSource())
    }

    fun registerEmaiRepository() : RegisterRepository{
        return RegisterRepository(FakeRegisterDataSource())
    }
}
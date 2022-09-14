package com.example.clone_instagram.common.base

import com.example.clone_instagram.home.data.FeedMemoryCache
import com.example.clone_instagram.home.data.HomeDataSourceFactory
import com.example.clone_instagram.home.data.HomeRepository
import com.example.clone_instagram.login.data.FakeDataSource
import com.example.clone_instagram.login.data.LoginRepository
import com.example.clone_instagram.profile.data.*
import com.example.clone_instagram.register.data.FakeRegisterDataSource
import com.example.clone_instagram.register.data.RegisterRepository
import com.example.clone_instagram.splash.data.FakeLocalDataSource
import com.example.clone_instagram.splash.data.SplashRepository

object DependencyInjector {
    fun splashRepository() : SplashRepository{
        return SplashRepository(FakeLocalDataSource())
    }

    fun loginRepository() : LoginRepository{
        return LoginRepository(FakeDataSource())
    }

    fun registerEmaiRepository() : RegisterRepository{
        return RegisterRepository(FakeRegisterDataSource())
    }

    fun profileRepository() : ProfileRepository{
        return ProfileRepository(ProfileDataSourceFactory(ProfileMemoryCache,PostsListMemoryCache))
    }

    fun homeRepository() : HomeRepository{
        return HomeRepository(HomeDataSourceFactory(FeedMemoryCache))
    }

}
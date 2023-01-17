package com.example.clone_instagram.common.base

import android.content.Context
import com.example.clone_instagram.add.data.AddFakeRemoteDataSource
import com.example.clone_instagram.add.data.AddLocalDataSource
import com.example.clone_instagram.add.data.AddRepository
import com.example.clone_instagram.home.data.FeedMemoryCache
import com.example.clone_instagram.home.data.HomeDataSourceFactory
import com.example.clone_instagram.home.data.HomeRepository
import com.example.clone_instagram.login.data.FakeDataSource
import com.example.clone_instagram.login.data.FireLoginDataSource
import com.example.clone_instagram.login.data.LoginRepository
import com.example.clone_instagram.post.data.PostLocalDataSource
import com.example.clone_instagram.post.data.PostRepository
import com.example.clone_instagram.profile.data.*
import com.example.clone_instagram.register.data.FakeRegisterDataSource
import com.example.clone_instagram.register.data.FireRegisterDataSource
import com.example.clone_instagram.register.data.RegisterRepository
import com.example.clone_instagram.search.data.SearchFakeRemoteDataSource
import com.example.clone_instagram.search.data.SearchRepository
import com.example.clone_instagram.splash.data.FakeLocalDataSource
import com.example.clone_instagram.splash.data.FireSplashLocalDataSource
import com.example.clone_instagram.splash.data.SplashRepository

object DependencyInjector {

    fun splashRepository() : SplashRepository{
        return SplashRepository(FireSplashLocalDataSource())
    }

    fun loginRepository() : LoginRepository{
        return LoginRepository(FireLoginDataSource())
    }

    fun registerEmaiRepository() : RegisterRepository{
        return RegisterRepository(FireRegisterDataSource())
    }

    fun searchRepository() : SearchRepository {
        return SearchRepository(SearchFakeRemoteDataSource())
    }

    fun profileRepository() : ProfileRepository{
        return ProfileRepository(ProfileDataSourceFactory(ProfileMemoryCache,PostsListMemoryCache))
    }

    fun homeRepository() : HomeRepository{
        return HomeRepository(HomeDataSourceFactory(FeedMemoryCache))
    }

    fun addRepository() : AddRepository{
        return AddRepository(AddFakeRemoteDataSource(), AddLocalDataSource())
    }

    fun postRepository(context : Context) : PostRepository{
        return PostRepository(PostLocalDataSource(context))
    }

}
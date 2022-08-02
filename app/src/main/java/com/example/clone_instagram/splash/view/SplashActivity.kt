package com.example.clone_instagram.splash.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clone_instagram.R
import com.example.clone_instagram.common.base.DependencyInjector
import com.example.clone_instagram.databinding.ActivitySplashBinding
import com.example.clone_instagram.splash.Splash
import com.example.clone_instagram.splash.presentation.SplashPresenter

class SplashActivity : AppCompatActivity(), Splash.View {

    private lateinit var binding: ActivitySplashBinding

    override lateinit var presenter: Splash.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val repository = DependencyInjector.splashRepository()
        presenter = SplashPresenter(this,repository)
    }

    override fun goToMainScreen() {

    }

    override fun goToLoginScreen() {

    }
}
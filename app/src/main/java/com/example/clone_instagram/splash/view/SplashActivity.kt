package com.example.clone_instagram.splash.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import com.example.clone_instagram.R
import com.example.clone_instagram.common.base.DependencyInjector
import com.example.clone_instagram.common.extension.animationEnd
import com.example.clone_instagram.databinding.ActivitySplashBinding
import com.example.clone_instagram.login.view.LoginActivity
import com.example.clone_instagram.main.view.MainActivity
import com.example.clone_instagram.splash.Splash
import com.example.clone_instagram.splash.presentation.SplashPresenter

@SuppressLint("CustomSplashScreen")
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

        binding.splashImg.animate().apply {
            setListener(animationEnd {
                presenter.authenticated()
            })
            duration = 1000
            alpha(1.0f)
            start()
        }
    }

    override fun goToMainScreen() {
        binding.splashImg.animate().apply{
            setListener(
                animationEnd {
                    val intent = Intent(baseContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                }
            )
            duration = 1000
            startDelay = 1000
            alpha(0.0f)
            start()
        }

    }

    override fun goToLoginScreen() {
        binding.splashImg.animate().apply{
            setListener(
                animationEnd {
                    val intent = Intent(baseContext, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                }
            )
            duration = 1000
            startDelay = 1000
            alpha(0.0f)
            start()
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}
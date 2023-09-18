package com.example.clone_instagram.main.view

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.clone_instagram.R
import com.example.clone_instagram.post.view.AddFragment
import com.example.clone_instagram.common.extension.replaceFragment
import com.example.clone_instagram.databinding.ActivityMainBinding
import com.example.clone_instagram.home.view.HomeFragment
import com.example.clone_instagram.main.LogoutListener
import com.example.clone_instagram.profile.view.ProfileFragment
import com.example.clone_instagram.search.view.SearchFragment
import com.example.clone_instagram.splash.presentation.SplashPresenter
import com.example.clone_instagram.splash.view.SplashActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, AddFragment.AddListerner,
SearchFragment.SearchLisener, LogoutListener, ProfileFragment.FollowListener
{


    private lateinit var binding: ActivityMainBinding

    private lateinit var homeFragment: HomeFragment
    private lateinit var addFragment: AddFragment
    private  lateinit var profileFragment: ProfileFragment
    private lateinit var searchFragment: SearchFragment
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        /**
         * Customização da status bar
         */

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            when(resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)){
                Configuration.UI_MODE_NIGHT_YES -> {
                    window.statusBarColor = ContextCompat.getColor(this,R.color.black)
                    binding.mainImgLogo.imageTintList = ColorStateList.valueOf(Color.WHITE)
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    window.insetsController?.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )

                    window.statusBarColor = ContextCompat.getColor(this,R.color.gray)
                }
            }
        }

        setSupportActionBar(binding.mainToolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_insta_camera)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        homeFragment = HomeFragment()
        addFragment = AddFragment()
        profileFragment = ProfileFragment()
        searchFragment = SearchFragment()

//        currentFragment = homeFragment

        binding.mainBottomNav.setOnNavigationItemSelectedListener(this)
        binding.mainBottomNav.selectedItemId = R.id.menu_bottom_home




    }


    private fun setScrollToolbarEnabled(enabled: Boolean) {
        val params = binding.mainToolbar.layoutParams as AppBarLayout.LayoutParams
        var coordinatorParams = binding.mainAppbar.layoutParams as CoordinatorLayout.LayoutParams

        if (enabled){
            params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            coordinatorParams.behavior = AppBarLayout.Behavior()
        }else{
            params.scrollFlags = 0
            coordinatorParams.behavior = null
        }
        binding.mainAppbar.layoutParams = coordinatorParams

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var scrollToolbarEnbled = false
        //V1
        when(item.itemId){
            R.id.menu_bottom_home -> {
                if (currentFragment == homeFragment)return false
                currentFragment = homeFragment
            }
            R.id.menu_bottom_search -> {
                if (currentFragment == searchFragment)return false
                currentFragment = searchFragment
                scrollToolbarEnbled = false
            }
            R.id.menu_bottom_add ->{
                if (currentFragment == addFragment)return false
                currentFragment = addFragment
                scrollToolbarEnbled = false
            }
            R.id.menu_bottom_profile ->{
                if (currentFragment == profileFragment)return false
                currentFragment = profileFragment
                scrollToolbarEnbled = true
            }
        }

        setScrollToolbarEnabled(scrollToolbarEnbled)

        currentFragment?.let {
           replaceFragment(R.id.main_fragment,it)
        }

        return true
    }

    override fun onPostCreated() {
        homeFragment.presenter.clear()

        if(supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null){
            profileFragment.presenter.clear()
        }

       binding.mainBottomNav.selectedItemId = R.id.menu_bottom_home
    }

    override fun goToProfile(uuid: String) {
        val fragment = ProfileFragment().apply {
            arguments = Bundle().apply {
                putString(ProfileFragment.KEY_USER_ID,uuid)
            }
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment,fragment,fragment.javaClass.simpleName + "detail")
            addToBackStack(null)
            commit()
        }
    }

    override fun followUpdate() {
        homeFragment.presenter.clear()

        if(supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null){
            profileFragment.presenter.clear()
        }
    }

    override fun logout() {
        if(supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null){
            profileFragment.presenter.clear()
        }

        homeFragment.presenter.clear()
        homeFragment.presenter.logout()

        val intent = Intent(baseContext,SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
    }




}
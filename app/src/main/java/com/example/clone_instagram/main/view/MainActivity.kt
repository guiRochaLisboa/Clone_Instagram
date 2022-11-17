package com.example.clone_instagram.main.view

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
import com.example.clone_instagram.profile.view.ProfileFragment
import com.example.clone_instagram.search.view.SearchFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, AddFragment.AddListerner {


    private lateinit var binding: ActivityMainBinding

    private lateinit var homeFragment: HomeFragment
    private lateinit var addFragment: AddFragment
    private  lateinit var profileFragment: ProfileFragment
    private lateinit var searchFragment: SearchFragment
    private var currentFragment: Fragment? = null

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        /**
         * Customização da status bar
         */

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                ,WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

            window.statusBarColor = ContextCompat.getColor(this,R.color.gray)

        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        homeFragment = HomeFragment()
        addFragment = AddFragment()
        profileFragment = ProfileFragment()
        searchFragment = SearchFragment()

//        currentFragment = homeFragment

        binding.mainBottomNav.setOnNavigationItemSelectedListener(this)
        binding.mainBottomNav.selectedItemId = R.id.menu_bottom_home

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_insta_camera)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""


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


}
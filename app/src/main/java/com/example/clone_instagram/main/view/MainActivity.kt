package com.example.clone_instagram.main.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.clone_instagram.R
import com.example.clone_instagram.camera.view.CameraFragment
import com.example.clone_instagram.common.extension.replaceFragment
import com.example.clone_instagram.databinding.ActivityMainBinding
import com.example.clone_instagram.home.view.HomeFragment
import com.example.clone_instagram.profile.view.ProfileFragment
import com.example.clone_instagram.search.view.SearchFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    private lateinit var binding: ActivityMainBinding

    private lateinit var homeFragment: HomeFragment
    private lateinit var cameraFragment: CameraFragment
    private  lateinit var profileFragment: ProfileFragment
    private lateinit var searchFragment: SearchFragment
    private lateinit var currentFragment: Fragment

    private lateinit var fragmentSavedState: HashMap<String,Fragment.SavedState?>


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        /**
         * Customização da status bar
         */

        if(savedInstanceState == null){
            fragmentSavedState = HashMap()
        }else{
            savedInstanceState.getSerializable("fragmentState") as HashMap<String,Fragment.SavedState?>
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                ,WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

            window.statusBarColor = ContextCompat.getColor(this,R.color.gray)

        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)

//        homeFragment = HomeFragment()
//        cameraFragment = CameraFragment()
//        profileFragment = ProfileFragment()
//        searchFragment = SearchFragment()

      //  currentFragment = homeFragment

       // supportFragmentManager.beginTransaction().apply{
       //     add(R.id.main_fragment,profileFragment,"3").hide(profileFragment)
       //     add(R.id.main_fragment,cameraFragment,"2").hide(cameraFragment)
       //     add(R.id.main_fragment,searchFragment,"1").hide(searchFragment)
       //     add(R.id.main_fragment,homeFragment,"0")
       //     commit()
     //   }


        binding.mainBottomNav.setOnNavigationItemSelectedListener(this)
        binding.mainBottomNav.selectedItemId = R.id.menu_bottom_home

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_insta_camera)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""


    }

    /**
     * Método disparado toda vez que trocar e tiver que salvar o estado de um fragment
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("fragmentState", fragmentSavedState)
        super.onSaveInstanceState(outState)
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

        val newFrag: Fragment? = when(item.itemId){
            R.id.menu_bottom_home ->{
                HomeFragment()
            }
            R.id.menu_bottom_profile ->{
                ProfileFragment()
            }
            else -> null
        }

        val currFragment = supportFragmentManager.findFragmentById(R.id.main_fragment)
        val fragmentTag = newFrag?.javaClass?.simpleName

        if (!currFragment?.tag.equals(fragmentTag)){
            currFragment?.let {
                fragmentSavedState.put(
                    it.tag!!,
                    supportFragmentManager.saveFragmentInstanceState(it)
                )
            }
        }

        newFrag?.setInitialSavedState(fragmentSavedState[fragmentTag])
        newFrag?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment,it,fragmentTag)
                .addToBackStack(fragmentTag)
                .commit()
        }


        //V1
//        when(item.itemId){
//            R.id.menu_bottom_home -> {
//                if (currentFragment == homeFragment)return false
//                supportFragmentManager.beginTransaction().hide(currentFragment).show(homeFragment).commit()
//                currentFragment = homeFragment
//            }
//            R.id.menu_bottom_search -> {
//                if (currentFragment == searchFragment)return false
//                supportFragmentManager.beginTransaction().hide(currentFragment).show(searchFragment).commit()
//                currentFragment = searchFragment
//            }
//            R.id.menu_bottom_add ->{
//                if (currentFragment == cameraFragment)return false
//                supportFragmentManager.beginTransaction().hide(currentFragment).show(cameraFragment).commit()
//                currentFragment = cameraFragment
//            }
//            R.id.menu_bottom_profile ->{
//                if (currentFragment == profileFragment)return false
//                supportFragmentManager.beginTransaction().hide(currentFragment).show(profileFragment).commit()
//                currentFragment = profileFragment
//                scrollToolbarEnbled = true
//            }
//        }

        setScrollToolbarEnabled(scrollToolbarEnbled)

//        currentFragment?.let {
//           replaceFragment(R.id.main_fragment,it)
//        }

        return true
    }


}
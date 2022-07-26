package com.example.clone_instagram.register.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.clone_instagram.R
import com.example.clone_instagram.databinding.ActivityRegisterBinding
import com.example.clone_instagram.register.view.RegisterNamePasswordFragment.Companion.KEY_EMAIL

class RegisterActivity : AppCompatActivity(),FragmentAttachListener {

    private lateinit var bindign: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        bindign = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bindign.root)


        val fragment = RegisterEmailFragment()
        replaceFragment(fragment)
    }

    override fun goToNameAndPasswordScreen(email: String) {
        val fragment = RegisterNamePasswordFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_EMAIL,email)
            }
        }

        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment: Fragment){
        if (supportFragmentManager.findFragmentById(R.id.register_fragment) == null){
            supportFragmentManager.beginTransaction().apply {
                add(R.id.register_fragment, fragment) //replace utilizado para trocar o fragment
                commit()
            }
        }else{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.register_fragment, fragment) //replace utilizado para trocar o fragment
                addToBackStack(null)
                commit()
            }
        }


    }
}
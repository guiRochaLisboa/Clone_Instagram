package com.example.clone_instagram.common.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.clone_instagram.R

fun Activity.hideKeyBoard(){
    val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE)as InputMethodManager

    var view: View?  = currentFocus
    if(view != null){
        view = View(this)
    }

    imm.hideSoftInputFromWindow(view?.windowToken,0)
}

fun Activity.animationEnd(callback: () -> Unit) : AnimatorListenerAdapter {
    return object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            callback.invoke()
        }
    }
}


fun AppCompatActivity.replaceFragment(id: Int,fragment: Fragment){
    if (supportFragmentManager.findFragmentById(id) == null) {
        supportFragmentManager.beginTransaction().apply {
            add(id, fragment,fragment.javaClass.simpleName) //replace utilizado para trocar o fragment
            commit()
        }
    } else {
        supportFragmentManager.beginTransaction().apply {
            replace(id, fragment,fragment.javaClass.simpleName) //replace utilizado para trocar o fragment
            addToBackStack(null)
            commit()
        }
    }
}

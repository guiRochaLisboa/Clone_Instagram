package com.example.clone_instagram.register.view

interface FragmentAttachListener {
    fun goToNameAndPasswordScreen(email : String)
    fun goToWelcomeScreen(name: String)
    fun goToPhotoScreen()
    fun goToMainScreen()
    fun goToGalleryScreen()
    fun goToCameraScreen()
}
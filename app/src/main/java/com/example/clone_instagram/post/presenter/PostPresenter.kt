package com.example.clone_instagram.post.presenter

import android.net.Uri
import android.provider.ContactsContract
import android.util.Patterns
import com.example.clone_instagram.R
import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.model.DataBase
import com.example.clone_instagram.common.model.UserAuth
import com.example.clone_instagram.post.Post
import com.example.clone_instagram.post.data.PostRepository
import com.example.clone_instagram.profile.Profile
import com.example.clone_instagram.profile.data.ProfileRepository
import com.example.clone_instagram.profile.view.ProfileFragment
import com.example.clone_instagram.register.RegisterEmail
import com.example.clone_instagram.register.data.RegisterCallback
import com.example.clone_instagram.register.data.RegisterRepository
import kotlinx.coroutines.*
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

class PostPresenter(
    private var view: Post.View?,
    private val repository: PostRepository
) : Post.Presenter, CoroutineScope {

    private var uri: Uri? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO


    override fun fetchPictures() {

        //Chamada na thread main (UI)
        view?.showProgress(true)

        launch {
            //Chamada paralela (corotina IO)
            val pictures = repository.fetchPictures()

            withContext(Dispatchers.Main) {
                if (pictures.isEmpty()) {
                    view?.displayEmptyPictures()
                } else {
                    view?.displayFullPictures(pictures)
                }

                view?.showProgress(false)
            }
        }
    }

    override fun selectUri(uri: Uri) {
        this.uri = uri
    }

    override fun getSelectedUri(): Uri? {
        return uri
    }


    override fun onDestroy() {
        job.cancel()
        view = null
    }
}
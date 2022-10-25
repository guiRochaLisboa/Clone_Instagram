package com.example.clone_instagram.add.data.presentation

import android.net.Uri
import com.example.clone_instagram.add.Add
import com.example.clone_instagram.add.data.AddRepository
import com.example.clone_instagram.common.base.RequestCallback

class AddPresenter(
    private val view : Add.View? = null,
    private val repository : AddRepository
) : Add.Presenter {
    override fun createPost(uri: Uri, caption: String) {
        view?.showProgress(true)
        repository.createPost(uri,caption,object : RequestCallback<Boolean>{
            override fun onSucess(data: Boolean) {
                if(data){
                    view?.displayRequestSuccess()
                }else{
                    view?.displayRequestFailure("internal error")
                }
            }

            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }

            override fun onComplete() {
                view?.showProgress(false)
            }

        })
    }

    override fun onDestroy() {
        TODO("Not yet implemented")
    }
}
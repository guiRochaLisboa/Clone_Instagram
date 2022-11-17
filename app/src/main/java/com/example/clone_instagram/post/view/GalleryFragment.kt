package com.example.clone_instagram.post.view

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import com.example.clone_instagram.R
import com.example.clone_instagram.common.base.BaseFragment
import com.example.clone_instagram.common.base.DependencyInjector
import com.example.clone_instagram.databinding.FragmentGalleryBinding
import com.example.clone_instagram.post.Post
import com.example.clone_instagram.post.presenter.PostPresenter

class GalleryFragment : BaseFragment<FragmentGalleryBinding, Post.Presenter>(
    R.layout.fragment_gallery,
    FragmentGalleryBinding::bind
), Post.View {

    override lateinit var presenter: Post.Presenter
    private val adapter = PicturesAdapter() { uri ->
        binding?.galleryImg?.setImageURI(uri)
        binding?.galleryNested?.smoothScrollTo(0,0)
        presenter.selectUri(uri)
    }

    override fun setpuPresenter() {
        presenter = PostPresenter(this,DependencyInjector.postRepository(requireContext()))
    }

    override fun getMenu(): Int? = R.menu.menu_send

    override fun setupViews() {
        binding?.galleryRv?.layoutManager = GridLayoutManager(requireContext(),3)
        binding?.galleryRv?.adapter = adapter

        presenter.fetchPictures()
    }

    override fun showProgress(enabled: Boolean) {
        binding?.galleryProgress?.visibility = if(enabled) View.VISIBLE else View.GONE
    }

    override fun displayFullPictures(post: List<Uri>) {
        binding?.galleryTxtEmpty?.visibility = View.GONE
        binding?.galleryRv?.visibility = View.VISIBLE
        adapter.items = post
        adapter.notifyDataSetChanged()

        binding?.galleryImg?.setImageURI(post.first())
        binding?.galleryNested?.smoothScrollTo(0,0)
        presenter.selectUri(post.first())
    }

    override fun displayEmptyPictures() {
        binding?.galleryTxtEmpty?.visibility = View.VISIBLE
        binding?.galleryRv?.visibility = View.GONE
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_send -> {
                setFragmentResult("takePhotoKey", bundleOf("uri" to presenter.getSelectedUri()))
            }
        }
        return super.onOptionsItemSelected(item) //Método depreciado !*** BUSCAR SOLUÇÃO MAIS RECENTE ***!
    }


}
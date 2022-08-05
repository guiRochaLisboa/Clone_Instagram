package com.example.clone_instagram.profile.view

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clone_instagram.R
import com.example.clone_instagram.common.base.BaseFragment
import com.example.clone_instagram.common.base.DependencyInjector
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.UserAuth
import com.example.clone_instagram.databinding.FragmentProfileBinding
import com.example.clone_instagram.profile.Profile
import com.example.clone_instagram.profile.presentation.ProfilePresenter

class ProfileFragment : BaseFragment<FragmentProfileBinding, Profile.Presenter>(
    R.layout.fragment_profile,
    FragmentProfileBinding::bind
), Profile.View {

    override lateinit var presenter: Profile.Presenter

    private val adapter = PostAdapter()


    /**
     * Método disparado após a nossa fragment já estar criada
     */
    override fun setupViews() {
        binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(),3)
        binding?.profileRv?.adapter = adapter

        presenter.fetchUserProfile()
        presenter.fetchUserPost()
    }

    override fun setpuPresenter() {
        val repository = DependencyInjector.profileRepository()
        presenter = ProfilePresenter(this,repository)
    }

    override fun getMenu(): Int {
        return R.menu.menu_profile
    }

    override fun showProgress(enabled: Boolean) {
        binding?.profileProgress?.visibility = if(enabled) View.VISIBLE else View.GONE
    }

    override fun displayUserProfile(userAuth: UserAuth) {
        binding?.profileTxtPostsCount?.text = userAuth.postCount.toString()
        binding?.profileTxtFollowersCount?.text = userAuth.followersCount.toString()
        binding?.profileTxtFollowingCount?.text = userAuth.followingCount.toString()
        binding?.profileTxtUsername?.text = userAuth.name
        binding?.profileTxtBio?.text = "TODO"
        presenter.fetchUserPost()
    }

    override fun displayRequestFailure(message: String) {
    Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }

    override fun displayEmptyPost() {
        binding?.profileTxtEmpty?.visibility = View.VISIBLE
        binding?.profileRv?.visibility = View.GONE
    }

    override fun displayFullPost(post: List<Post>) {
        binding?.profileTxtEmpty?.visibility = View.GONE
        binding?.profileRv?.visibility = View.VISIBLE
        adapter.items = post
        adapter.notifyDataSetChanged()

    }











}
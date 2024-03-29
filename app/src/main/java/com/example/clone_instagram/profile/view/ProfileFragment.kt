package com.example.clone_instagram.profile.view

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.clone_instagram.R
import com.example.clone_instagram.common.base.BaseFragment
import com.example.clone_instagram.common.base.DependencyInjector
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.common.model.User
import com.example.clone_instagram.common.model.UserAuth
import com.example.clone_instagram.databinding.FragmentProfileBinding
import com.example.clone_instagram.main.LogoutListener
import com.example.clone_instagram.profile.Profile
import com.example.clone_instagram.profile.presentation.ProfilePresenter
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : BaseFragment<FragmentProfileBinding, Profile.Presenter>(
    R.layout.fragment_profile,
    FragmentProfileBinding::bind
), Profile.View, BottomNavigationView.OnNavigationItemSelectedListener {

    override lateinit var presenter: Profile.Presenter

    private val adapter = PostAdapter()
    private var uuid: String? = null

    private var logoutListener : LogoutListener? = null
    private var followListener: FollowListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is LogoutListener){
            logoutListener = context
        }
        if(context is FollowListener){
            followListener = context
        }
    }


    /**
     * Método disparado após a nossa fragment já estar criada
     */
    override fun setupViews() {
        uuid = arguments?.getString(KEY_USER_ID)

        binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.profileRv?.adapter = adapter
        binding?.profileNavTabs?.setOnNavigationItemSelectedListener(this)

        binding?.profileBtnEdit?.setOnClickListener {
            if (it.tag == true) {
                binding?.profileBtnEdit?.text = getString(R.string.follow)
                binding?.profileBtnEdit?.tag = false
                presenter.followUser(uuid,false)
            } else if (it.tag == false) {
                binding?.profileBtnEdit?.text = getString(R.string.unfollow)
                binding?.profileBtnEdit?.tag = true
                presenter.followUser(uuid,true)
            }
        }

        presenter.fetchUserProfile(uuid)
    }

    override fun setpuPresenter() {
        val repository = DependencyInjector.profileRepository()
        presenter = ProfilePresenter(this, repository)
    }

    override fun getMenu(): Int {
        return R.menu.menu_profile
    }

    override fun showProgress(enabled: Boolean) {
        binding?.profileProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayUserProfile(user: Pair<User, Boolean?>) {
        val (userAuth, following) = user

        binding?.profileTxtPostsCount?.text = userAuth.postCount.toString()
        binding?.profileTxtFollowersCount?.text = userAuth.followers.toString()
        binding?.profileTxtFollowingCount?.text = userAuth.following.toString()
        binding?.profileTxtUsername?.text = userAuth.name
        binding?.profileTxtBio?.text = "TODO"
        binding?.let {
            Glide.with(requireContext()).load(userAuth.photoUrl).into(it.profileImgIcon)
        }

        binding?.profileBtnEdit?.text = when (following) {
            null -> getString(R.string.edit_profile)
            true -> getString(R.string.unfollow)
            false -> getString(R.string.follow)
        }

        binding?.profileBtnEdit?.tag = following

        presenter.fetchUserPost(uuid)
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
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

    override fun followUpdate() {
        followListener?.followUpdate()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_profile_grid -> {
                binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
            }
            R.id.menu_profile_list -> {
                binding?.profileRv?.layoutManager = LinearLayoutManager(requireContext())
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_logout -> {
                logoutListener?.logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    interface FollowListener {
        fun followUpdate()
    }

    companion object {
        const val KEY_USER_ID = "key_user_id"
    }


}
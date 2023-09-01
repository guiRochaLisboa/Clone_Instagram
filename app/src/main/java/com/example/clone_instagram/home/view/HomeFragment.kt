package com.example.clone_instagram.home.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clone_instagram.R
import com.example.clone_instagram.common.base.BaseFragment
import com.example.clone_instagram.common.base.DependencyInjector
import com.example.clone_instagram.common.model.Post
import com.example.clone_instagram.databinding.FragmentHomeBinding
import com.example.clone_instagram.home.Home
import com.example.clone_instagram.home.presenter.HomePresenter
import com.example.clone_instagram.main.LogoutListener

class HomeFragment : BaseFragment<FragmentHomeBinding, Home.Presenter>(
    R.layout.fragment_home,
    FragmentHomeBinding::bind
), Home.View {


    override lateinit var presenter: Home.Presenter

    private val adapter = FeedAdapter()

    private var logoutListener : LogoutListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is LogoutListener){
            logoutListener = context
        }
    }

    override fun setupViews() {
        binding?.homeRv?.layoutManager = LinearLayoutManager(requireContext())
        binding?.homeRv?.adapter = adapter
        
        presenter.fetchFeed()
    }

    override fun setpuPresenter() {
        presenter = HomePresenter(this, DependencyInjector.homeRepository())
    }

    override fun getMenu(): Int? = R.menu.menu_profile


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun showProgress(enabled: Boolean) {
        binding?.homeProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun displayEmptyPost() {
        binding?.homeTxtEmpty?.visibility = View.VISIBLE
        binding?.homeRv?.visibility = View.GONE
    }

    override fun displayFullPost(post: List<Post>) {
        binding?.homeTxtEmpty?.visibility = View.GONE
        binding?.homeRv?.visibility = View.VISIBLE
        adapter.items = post
        adapter.notifyDataSetChanged()
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

}
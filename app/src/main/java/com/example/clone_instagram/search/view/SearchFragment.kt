package com.example.clone_instagram.search.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clone_instagram.R
import com.example.clone_instagram.common.base.BaseFragment
import com.example.clone_instagram.common.base.DependencyInjector
import com.example.clone_instagram.common.model.UserAuth
import com.example.clone_instagram.databinding.FragmentSearchBinding
import com.example.clone_instagram.search.Search
import com.example.clone_instagram.search.presenter.SearchPresenter

class SearchFragment : BaseFragment<FragmentSearchBinding,Search.Presenter>(
    R.layout.fragment_search,
    FragmentSearchBinding::bind
),Search.View {


    override lateinit var presenter: Search.Presenter

    //forma atrasada de inicializar um objeto (by lazy {})
    private val adapter by lazy {
        SearchAdapter(onItemClicked)
    }

    private var searchListener : SearchLisener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SearchLisener){
            searchListener = context
        }
    }

    override fun setupViews() {
        binding?.searchRv?.layoutManager = LinearLayoutManager(requireContext())
        binding?.searchRv?.adapter = adapter
    }

    override fun setpuPresenter() {
        val repository = DependencyInjector.searchRepository()
        presenter = SearchPresenter(this, repository)
    }

    private val onItemClicked : (String) -> Unit = {uuid ->
        searchListener?.goToProfile(uuid)
    }

    override fun getMenu() = R.menu.menu_search

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

            //getSystemService busca recursos do android (Input do teclado,bluetooth,alarme)
            val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
            val searchView = (menu.findItem(R.id.menu_search).actionView as SearchView)
            searchView.apply {
                setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
                setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if(newText?.isNotEmpty() == true){
                            presenter.fetchUsers(newText)
                            return true
                        }
                        return false
                    }

                })
            }

    }

    override fun showProgress(enabled: Boolean) {
        binding?.searchProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayFullUser(user: List<UserAuth>) {
        binding?.searchTxtEmpty?.visibility = View.GONE
        binding?.searchRv?.visibility = View.VISIBLE
        adapter.items = user
        adapter.notifyDataSetChanged()
    }

    override fun displayEmptyUsers() {
        binding?.searchTxtEmpty?.visibility = View.VISIBLE
        binding?.searchRv?.visibility = View.GONE
    }

    interface SearchLisener {
        fun goToProfile(uuid: String)
    }

}
package com.esgi4al.discooker.ui.fragments.users

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.ListableUser
import com.esgi4al.discooker.repositories.UsersListDataRepository
import com.esgi4al.discooker.ui.interfaces.FragmentNavigation
import com.esgi4al.discooker.ui.interfaces.UserItemClickHandler
import com.esgi4al.discooker.ui.recyclerViewAdapters.users.UsersRvAdapter
import com.esgi4al.discooker.ui.viewModels.UsersListViewModel
import com.esgi4al.discooker.ui.viewModels.factories.UsersListViewModelFactory

class UsersListFragment: Fragment(), UserItemClickHandler {

    private var fragmentNavigation: FragmentNavigation? = null
    private lateinit var usersListRv: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var searchResultsTv: TextView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private var currentSearch: String? = ""
    private var searchRunnable: Runnable? = null
    private val searchHandler = Handler(Looper.getMainLooper())

    private val usersListViewModel: UsersListViewModel by viewModels {
        UsersListViewModelFactory(UsersListDataRepository(), this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            fragmentNavigation = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_users_list, container, false)
        swipeRefreshLayout = view.findViewById(R.id.users_list_swipe_refresh_layout)
        searchView = view.findViewById(R.id.searchBarUsers)
        searchResultsTv = view.findViewById(R.id.users_search_results_tv)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSearchView()

        usersListViewModel.users.observe(viewLifecycleOwner) { users ->
            val searchResultsText = if (users.isEmpty()) {
                "Aucun utilisateur ne correspond à votre recherche."
            } else {
                "${users.size} utilisateur(s) trouvé(s)."
            }
            searchResultsTv.text = searchResultsText
            setUpUsersRv(users, view)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setUpSearchView() {
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                swipeRefreshLayout.isRefreshing = false
                currentSearch = newText

                searchRunnable?.let { searchHandler.removeCallbacks(it) }

                searchRunnable = Runnable {
                    usersListViewModel.getUsersBySearch(currentSearch)
                }

                searchHandler.postDelayed(searchRunnable!!, 200)
                return true
            }
        })
    }


    private fun setUpUsersRv(users: List<ListableUser>, fragmentView: View) {
        usersListRv = fragmentView.findViewById(R.id.users_list_rv)
        usersListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        usersListRv.adapter = UsersRvAdapter(users, this)
    }

    override fun onUserClick(userId: String) {
        fragmentNavigation?.loadFragment(UserProfileFragment.newInstance(userId))
    }
}
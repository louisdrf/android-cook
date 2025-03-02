package com.esgi4al.discooker.ui.fragments

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
import com.esgi4al.discooker.models.Category
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.models.Region
import com.esgi4al.discooker.repositories.HomePageGlobalDataRepository
import com.esgi4al.discooker.service.ApiClient
import com.esgi4al.discooker.service.GlobalDataService
import com.esgi4al.discooker.ui.fragments.recipes.RecipeDetailFragment
import com.esgi4al.discooker.ui.fragments.users.UserProfileFragment
import com.esgi4al.discooker.ui.interfaces.FragmentNavigation
import com.esgi4al.discooker.ui.interfaces.HomePageItemsClickHandler
import com.esgi4al.discooker.ui.interfaces.UserItemClickHandler
import com.esgi4al.discooker.ui.recyclerViewAdapters.recipes.CategoriesRVAdapter
import com.esgi4al.discooker.ui.recyclerViewAdapters.recipes.RecipesRVAdapter
import com.esgi4al.discooker.ui.recyclerViewAdapters.recipes.RegionsRVAdapter
import com.esgi4al.discooker.ui.viewModels.HomePageViewModel
import com.esgi4al.discooker.ui.viewModels.factories.ViewModelFactory

class HomePageFragment: Fragment(), HomePageItemsClickHandler, UserItemClickHandler {

    private lateinit var searchResultsTv: TextView
    private lateinit var categoriesRv: RecyclerView
    private lateinit var regionsRv: RecyclerView
    private lateinit var recipesRv: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var selectedCategoryName: String? = ""
    private var selectedRegionName: String? = ""
    private var currentSearch: String? = ""
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private var fragmentNavigation: FragmentNavigation? = null
    private var searchRunnable: Runnable? = null
    private val searchHandler = Handler(Looper.getMainLooper())

    private val homeViewModel: HomePageViewModel by viewModels {
        val service: GlobalDataService = ApiClient.getGlobalDataService()
        val repository = HomePageGlobalDataRepository(service)
        ViewModelFactory(repository, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)
        searchResultsTv = view.findViewById(R.id.search_results_tv)
        swipeRefreshLayout = view.findViewById(R.id.home_swipe_refresh_layout)
        searchView = view.findViewById(R.id.searchBarRecipes)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSwipeToRefreshListeners()
        setUpSearchView()

        homeViewModel.categories.observe(viewLifecycleOwner) { categories ->
            setUpCategoriesRv(categories, view)
        }

        homeViewModel.regions.observe(viewLifecycleOwner) { regions ->
            setUpRegionsRv(regions, view)
        }

        homeViewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            val resultsText = if (recipes.isEmpty()) {
                "Aucune recette ne correspond à votre recherche."
            } else {
                "${recipes.size} recettes trouvées."
            }
            searchResultsTv.text = resultsText
            setUpRecipesRv(recipes, view)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            fragmentNavigation = context
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
                    homeViewModel.fetchRecipesBySearch(newText, selectedCategoryName, selectedRegionName)
                }

                searchHandler.postDelayed(searchRunnable!!, 200)
                return true
            }
        })
    }

    private fun setUpRecipesRv(recipes: List<Recipe>, fragmentView: View) {
        recipesRv = fragmentView.findViewById(R.id.home_page_recipes_rv)
        recipesRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recipesRv.adapter = RecipesRVAdapter(recipes, this, this)
    }

    private fun setUpCategoriesRv(categories: List<Category>, fragmentView: View) {
        categoriesRv = fragmentView.findViewById(R.id.home_page_categories_rv)
        categoriesRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        categoriesRv.adapter = CategoriesRVAdapter(categories, this)
    }

    private fun setUpRegionsRv(regions: List<Region>, fragmentView: View) {
        regionsRv = fragmentView.findViewById(R.id.home_page_regions_rv)
        regionsRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        regionsRv.adapter = RegionsRVAdapter(regions, this)
    }

    private fun setUpSwipeToRefreshListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.fetchRecipesBySearch(currentSearch, selectedCategoryName, selectedRegionName)
        }
    }

    override fun onRecipeClick(recipeId: String) {
        fragmentNavigation?.loadFragment(RecipeDetailFragment.newInstance(recipeId))
    }

    override fun onCategoryClick(categoryName: String) {
        swipeRefreshLayout.isRefreshing = true
        selectedCategoryName = categoryName
        homeViewModel.fetchRecipesBySearch(currentSearch, selectedCategoryName, selectedRegionName)
    }

    override fun onRegionClick(regionName: String) {
        swipeRefreshLayout.isRefreshing = true
        selectedRegionName = regionName
        homeViewModel.fetchRecipesBySearch(currentSearch, selectedCategoryName, selectedRegionName)
    }

    override fun onCategoryCloseIconClick() {
        swipeRefreshLayout.isRefreshing = true
        selectedCategoryName = ""
        homeViewModel.fetchRecipesBySearch(currentSearch, selectedCategoryName, selectedRegionName)
    }

    override fun onRegionCloseIconClick() {
        swipeRefreshLayout.isRefreshing = true
        selectedRegionName = ""
        homeViewModel.fetchRecipesBySearch(currentSearch, selectedCategoryName, selectedRegionName)
    }

    override fun onUserClick(userId: String) {
        fragmentNavigation?.loadFragment(UserProfileFragment.newInstance(userId))
    }
}
package com.esgi4al.discooker.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.esgi4al.discooker.ui.interfaces.HomePageItemsClickHandler
import com.esgi4al.discooker.ui.recyclerViewAdapters.CategoriesRVAdapter
import com.esgi4al.discooker.ui.recyclerViewAdapters.RecipesRVAdapter
import com.esgi4al.discooker.ui.recyclerViewAdapters.RegionsRVAdapter
import com.esgi4al.discooker.ui.viewModels.HomePageViewModel
import com.esgi4al.discooker.ui.viewModels.factories.HomePageViewModelFactory

class HomePageFragment: Fragment(), HomePageItemsClickHandler {

    private lateinit var categoriesRv: RecyclerView
    private lateinit var regionsRv: RecyclerView
    private lateinit var recipesRv: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val homeViewModel: HomePageViewModel by viewModels {
        HomePageViewModelFactory(HomePageGlobalDataRepository(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)
        swipeRefreshLayout = view.findViewById(R.id.home_swipe_refresh_layout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSwipeToRefreshListeners()

        homeViewModel.categories.observe(viewLifecycleOwner) { categories ->
            setUpCategoriesRv(categories, view)
        }

        homeViewModel.regions.observe(viewLifecycleOwner) { regions ->
            setUpRegionsRv(regions, view)
        }

        homeViewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            setUpRecipesRv(recipes, view)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setUpRecipesRv(recipes: List<Recipe>, fragmentView: View) {
        recipesRv = fragmentView.findViewById(R.id.home_page_recipes_rv)
        recipesRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recipesRv.adapter = RecipesRVAdapter(recipes, this)
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
            homeViewModel.refreshRecipes()
        }
    }

    override fun onRecipeClick(recipeId: String) {
        TODO("Not yet implemented")
    }

    override fun onCategoryClick(categoryName: String) {
        swipeRefreshLayout.isRefreshing = true
        homeViewModel.fetchRecipesByCategoryName(categoryName)
    }

    override fun onRegionClick(regionName: String) {
        swipeRefreshLayout.isRefreshing = true
        homeViewModel.fetchRecipesByRegionName(regionName)
    }
}
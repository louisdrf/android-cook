package com.esgi4al.discooker.ui.viewModels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.esgi4al.discooker.models.Category
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.models.Region
import com.esgi4al.discooker.repositories.HomePageGlobalDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePageViewModel(
    private val globalDataRepo: HomePageGlobalDataRepository,
    val context: LifecycleOwner
) : ViewModel() {

    val categories: LiveData<List<Category>> = liveData(Dispatchers.IO) {
        emit(globalDataRepo.getAllCategories() ?: emptyList())
    }

    val regions: LiveData<List<Region>> = liveData(Dispatchers.IO) {
        emit(globalDataRepo.getAllRegions() ?: emptyList())
    }

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> get() = _recipes

    init {
        fetchAllRecipes()
    }

    fun refreshRecipes() {
        fetchAllRecipes()
    }

    fun fetchRecipesByCategoryName(categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedRecipes = globalDataRepo.getAllRecipesByCategoryName(categoryName) ?: emptyList()
            _recipes.postValue(fetchedRecipes)
        }
    }

    fun fetchRecipesByRegionName(regionName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedRecipes = globalDataRepo.getAllRecipesByRegionName(regionName) ?: emptyList()
            _recipes.postValue(fetchedRecipes)
        }
    }

    private fun fetchAllRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedRecipes = globalDataRepo.getAllRecipes() ?: emptyList()
            _recipes.postValue(fetchedRecipes)
        }
    }
}
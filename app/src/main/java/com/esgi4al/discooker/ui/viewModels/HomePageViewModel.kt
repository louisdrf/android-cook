package com.esgi4al.discooker.ui.viewModels

import android.util.Log
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
        fetchRecipes()
    }

    private fun fetchRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedRecipes = globalDataRepo.getAllRecipes() ?: emptyList()
            Log.d("HOME VIEW MODEL", fetchedRecipes.toString())
            _recipes.postValue(fetchedRecipes)
        }
    }

    fun refreshRecipes() {
        fetchRecipes()
    }
}
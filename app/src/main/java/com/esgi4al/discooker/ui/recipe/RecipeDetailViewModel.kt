package com.esgi4al.discooker.ui.recipe

import androidx.lifecycle.MutableLiveData
import com.esgi4al.discooker.models.RecipeModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi4al.discooker.RetrofitClient.RecipeApi
import kotlinx.coroutines.launch

class RecipeDetailViewModel : ViewModel() {
    private val _recipe = MutableLiveData<RecipeModel>()

    val recipe: MutableLiveData<RecipeModel>
        get() = _recipe

    fun getRecipeDetails() {
        viewModelScope.launch {
            _recipe.value = RecipeApi.retrofitService.getRecipeDetails()
        }
    }
}
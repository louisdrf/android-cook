package com.esgi4al.discooker.ui.recipe

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.esgi4al.discooker.models.RecipeModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi4al.discooker.RetrofitClient.RecipeApi
import kotlinx.coroutines.launch

class RecipeDetailViewModel : ViewModel() {
    private val _recipe = MutableLiveData<RecipeModel>()

    val recipe: MutableLiveData<RecipeModel> get() = _recipe

    fun getRecipeDetails() {
        viewModelScope.launch {
            try {
                val result = RecipeApi.retrofitService.getRecipeDetails("670d70b78c302e46bf700145")
                _recipe.postValue(result)
            } catch (e: Exception) {
                Log.e("RecipeDetailViewModel", "Erreur inattendue: ${e.message}")
            }
        }
    }
}
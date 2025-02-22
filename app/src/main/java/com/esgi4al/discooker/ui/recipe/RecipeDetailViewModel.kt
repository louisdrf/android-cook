package com.esgi4al.discooker.ui.recipe

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.esgi4al.discooker.models.RecipeModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi4al.discooker.models.CommentRequest
import com.esgi4al.discooker.service.ApiClient
import kotlinx.coroutines.launch

class RecipeDetailViewModel : ViewModel() {
    private val recipeService = ApiClient.getRecipeService()
    private val _recipe = MutableLiveData<RecipeModel>()

    val recipe: MutableLiveData<RecipeModel> get() = _recipe

    fun getRecipeDetails() {
        viewModelScope.launch {
            try {
                val result = recipeService.getRecipeDetails("672a861d4c3ba57b722c6534")
                _recipe.postValue(result)
            } catch (e: Exception) {
                Log.e("RecipeDetailViewModel", "Erreur inattendue lors de la récupération de la recette: ${e.message}")
            }
        }
    }

    fun postRecipeComment(comment: String) {
        viewModelScope.launch {
            try {
                val result = recipeService.postRecipeComment("672a861d4c3ba57b722c6534", CommentRequest(comment))
                _recipe.postValue(result)
            } catch (e: Exception) {
                Log.e("RecipeDetailViewModel", "Erreur inattendue lors de l'envoi de commentaire: ${e.message}")
            }
        }
    }
}
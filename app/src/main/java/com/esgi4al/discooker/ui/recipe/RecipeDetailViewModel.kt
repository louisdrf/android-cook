package com.esgi4al.discooker.ui.recipe

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi4al.discooker.models.CommentRequest
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.service.ApiClient
import kotlinx.coroutines.launch

class RecipeDetailViewModel : ViewModel() {
    private val recipeService = ApiClient.getRecipeService()
    private val _recipe = MutableLiveData<Recipe>()
    private val _isLiked = MutableLiveData<Boolean>()

    val recipe: MutableLiveData<Recipe> get() = _recipe
    val isLiked: MutableLiveData<Boolean> get() = _isLiked

    fun getRecipeDetails(recipeId: String) {
        viewModelScope.launch {
            try {
                val result = recipeService.getRecipeDetails(recipeId)
                _recipe.postValue(result)
            } catch (e: Exception) {
                Log.e("RecipeDetailViewModel", "Erreur inattendue lors de la récupération de la recette: ${e.message}")
            }
        }
    }

    fun postRecipeComment(recipeId: String, comment: String, context: Context) {
        viewModelScope.launch {
            try {
                val sharedPreferences =
                    context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("auth_token", "") ?: ""

                if (token.isNotEmpty()) {
                    val result = recipeService.postRecipeComment(recipeId, "Bearer $token", CommentRequest(comment))
                    _recipe.postValue(result)
                } else {
                    Log.e("RecipeDetailViewModel", "Token manquant")
                }
            } catch (e: Exception) {
                Log.e("RecipeDetailViewModel", "Erreur inattendue lors de l'envoi de commentaire: ${e.message}")
            }
        }
    }

    fun isRecipeLiked(recipeId: String, context: Context) {
        viewModelScope.launch {
            try {
                val sharedPreferences =
                    context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("auth_token", "") ?: ""

                if (token.isNotEmpty()) {
                    val result = recipeService.isRecipeLiked(recipeId, "Bearer $token")
                    _isLiked.postValue(result)
                } else {
                    Log.e("RecipeDetailViewModel", "Token manquant")
                }
            } catch (e: Exception) {
                Log.e("RecipeDetailViewModel", "Erreur inattendue lors de l'envoi de commentaire: ${e.message}")
            }
        }
    }

    fun toggleLikeRecipe(recipeId: String, context: Context, isLiked: Boolean) {
        viewModelScope.launch {
            try {
                val sharedPreferences =
                    context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("auth_token", "") ?: ""

                if (token.isNotEmpty()) {
                    recipeService.toggleLikeRecipe(recipeId, "Bearer $token")
                    _isLiked.postValue(isLiked)
                } else {
                    Log.e("RecipeDetailViewModel", "Token manquant")
                }
            } catch (e: Exception) {
                Log.e("RecipeDetailViewModel", "Erreur inattendue sur le suivi de recette: ${e.message}")
            }
        }
    }
}
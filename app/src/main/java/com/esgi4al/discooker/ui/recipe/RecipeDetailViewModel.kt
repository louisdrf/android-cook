package com.esgi4al.discooker.ui.recipe

import android.content.Context
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

    fun postRecipeComment(comment: String, context: Context) {
        viewModelScope.launch {
            try {
                val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("auth_token", "") ?: ""

                if (token.isNotEmpty()) {
                    val result = recipeService.postRecipeComment("672a861d4c3ba57b722c6534", "Bearer $token", CommentRequest(comment))
                    _recipe.postValue(result)
                } else {
                    Log.e("RecipeDetailViewModel", "Token manquant")
                }
            } catch (e: Exception) {
                Log.e("RecipeDetailViewModel", "Erreur inattendue lors de l'envoi de commentaire: ${e.message}")
            }
        }
    }
}
package com.esgi4al.discooker.ui.viewModels

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.repositories.account.AccountDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountFavoritesViewModel(
    private val accountDataRepository: AccountDataRepository,
    val context: LifecycleOwner
): ViewModel() {

    private val _likedRecipes = MutableLiveData<List<Recipe>>()
    val likedRecipes: LiveData<List<Recipe>> get() = _likedRecipes

    fun getUserLikedRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            val recipes = accountDataRepository.getConnectedUserLikedRecipes() ?: emptyList()
            _likedRecipes.postValue(recipes)
        }
    }

    suspend fun deleteFavoriteRecipe(recipeId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val isSuccessful = accountDataRepository.deleteUserFavoriteRecipe(recipeId)
            if (isSuccessful) {
                _likedRecipes.postValue(_likedRecipes.value?.filter { it._id != recipeId })
            }
            isSuccessful
        }
    }
}
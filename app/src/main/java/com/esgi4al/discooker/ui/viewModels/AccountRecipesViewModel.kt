package com.esgi4al.discooker.ui.viewModels

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

class AccountRecipesViewModel(
    private val accountDataRepository: AccountDataRepository,
    val context: LifecycleOwner
): ViewModel() {

    private val _createdRecipes = MutableLiveData<List<Recipe>>()
    val createdRecipes: LiveData<List<Recipe>> get() = _createdRecipes

    fun getUserCreatedRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            val recipes = accountDataRepository.getConnectedUserRecipes() ?: emptyList()
            _createdRecipes.postValue(recipes)
        }
    }

    suspend fun deleteRecipe(recipeId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val isSuccessful = accountDataRepository.deleteUserRecipe(recipeId)
            if (isSuccessful) {
                _createdRecipes.postValue(_createdRecipes.value?.filter { it._id != recipeId })
            }
            isSuccessful
        }
    }
}
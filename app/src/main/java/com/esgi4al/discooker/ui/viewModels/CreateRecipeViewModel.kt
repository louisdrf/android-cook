package com.esgi4al.discooker.ui.viewModels

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.esgi4al.discooker.models.ApiRequestPostRecipe
import com.esgi4al.discooker.models.Category
import com.esgi4al.discooker.models.Ingredient
import com.esgi4al.discooker.models.Instruction
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.models.Region
import com.esgi4al.discooker.repositories.CreateRecipeGlobalDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateRecipeViewModel(
    private val globalDataRepository: CreateRecipeGlobalDataRepository,
    val context: LifecycleOwner
) : ViewModel() {
    val categories: LiveData<List<Category>> = liveData(Dispatchers.IO) {
        emit(globalDataRepository.getAllCategories() ?: emptyList())
    }
    val regions: LiveData<List<Region>> = liveData(Dispatchers.IO) {
        emit(globalDataRepository.getAllRegions() ?: emptyList())
    }

    private val _searchIngredients = MutableLiveData<List<Ingredient>>(mutableListOf())
    val searchIngredients: LiveData<List<Ingredient>> get() = _searchIngredients

    fun fetchIngredientsBySearch(ingredientName: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedIngredients = globalDataRepository.getIngredientsBySearch(ingredientName) ?: emptyList()
            _searchIngredients.postValue(fetchedIngredients)
        }
    }

    private val _instructions = MutableLiveData<List<Instruction>>(mutableListOf())
    val instructions: LiveData<List<Instruction>> get() = _instructions

    fun addInstruction(text: String) {
        val newInstruction = Instruction(
            step = (_instructions.value?.size ?: 0) + 1,
            instruction = text
        )
        _instructions.value = _instructions.value?.plus(newInstruction)
    }

    fun removeInstruction(instruction: Instruction) {
        _instructions.value = _instructions.value?.toMutableList()?.apply {
            remove(instruction)
        }
    }

    private val _ingredients = MutableLiveData<List<Ingredient>>(mutableListOf())
    val ingredients: LiveData<List<Ingredient>> get() = _ingredients

    fun addIngredient(ingredient: Ingredient) {
        _ingredients.value = _ingredients.value?.plus(ingredient)
    }

    fun removeIngredient(ingredient: Ingredient) {
        _ingredients.value = _ingredients.value?.toMutableList()?.apply {
            remove(ingredient)
        }
    }

    private val _createdRecipeId = MutableLiveData<String?>()
    val createdRecipeId: LiveData<String?> get() = _createdRecipeId

    fun submitRecipe(title: String, description: String, category: Category, region: Region) {
        viewModelScope.launch {
            try {
                val recipe = ApiRequestPostRecipe(title, description, _ingredients.value ?: emptyList(), _instructions.value ?: emptyList(), category, region)
                val response = globalDataRepository.postRecipe(recipe)
                _createdRecipeId.postValue(response?._id)
            } catch (e: Exception) {
                Log.e("CreateRecipeViewModel", "Error submitting recipe", e)
            }
        }
    }
}
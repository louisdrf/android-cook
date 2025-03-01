package com.esgi4al.discooker.repositories

import android.util.Log
import com.esgi4al.discooker.models.ApiRequestPostRecipe
import com.esgi4al.discooker.models.Category
import com.esgi4al.discooker.models.Ingredient
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.models.Region
import com.esgi4al.discooker.service.GlobalDataService
import com.esgi4al.discooker.service.RecipeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class CreateRecipeGlobalDataRepository(
    private var globalDataService: GlobalDataService,
    private var recipeService: RecipeService
) {

    suspend fun postRecipe(recipe: ApiRequestPostRecipe): Recipe? {
        return try {
            withContext(Dispatchers.IO) {
                recipeService.createRecipe(recipe)
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getIngredientsBySearch(ingredientName: String?): List<Ingredient>? {
        return try {
            withContext(Dispatchers.IO) {
                globalDataService.getIngredientsBySearch(ingredientName)
            }
        }  catch (e: Exception) {
            null
        }
    }

    suspend fun getAllCategories(): List<Category>? {
        return try {
            withContext(Dispatchers.IO) {
                globalDataService.getCategories()
            }
        }  catch (e: Exception) {
            null
        }
    }

    suspend fun getAllRegions(): List<Region>? {
        return try {
            withContext(Dispatchers.IO) {
                globalDataService.getRegions()
            }
        } catch (e: Exception) {
            null
        }
    }
}
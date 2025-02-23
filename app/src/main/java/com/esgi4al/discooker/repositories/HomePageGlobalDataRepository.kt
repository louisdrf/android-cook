package com.esgi4al.discooker.repositories

import android.util.Log
import com.esgi4al.discooker.models.Category
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.models.Region
import com.esgi4al.discooker.service.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException


class HomePageGlobalDataRepository {

    private val globalDataService = ApiClient.getGlobalDataService()

    suspend fun getAllRecipesByCategoryName(categoryName: String): List<Recipe>? {
        return try {
            withContext(Dispatchers.IO) {
                globalDataService.getRecipesByCategory(categoryName)
            }
        } catch (e: HttpException) {
            Log.e("API_ERROR", "HTTP Error: ${e.code()} - ${e.message()}")
            null
        } catch (e: Exception) {
            Log.e("API_ERROR", "Network Error: ${e.message}")
            null
        }
    }


    suspend fun getAllRecipesByRegionName(regionName: String): List<Recipe>? {
        return try {
            withContext(Dispatchers.IO) {
                globalDataService.getRecipesByRegion(regionName)
            }
        } catch (e: HttpException) {
            Log.e("API_ERROR", "HTTP Error: ${e.code()} - ${e.message()}")
            null
        } catch (e: Exception) {
            Log.e("API_ERROR", "Network Error: ${e.message}")
            null
        }
    }

    suspend fun getAllCategories(): List<Category>? {
        return try {
            withContext(Dispatchers.IO) {
                globalDataService.getCategories()
            }
        } catch (e: HttpException) {
            Log.e("API_ERROR", "HTTP Error: ${e.code()} - ${e.message()}")
            null
        } catch (e: Exception) {
            Log.e("API_ERROR", "Network Error: ${e.message}")
            null
        }
    }

    suspend fun getAllRegions(): List<Region>? {
        return try {
            withContext(Dispatchers.IO) {
                globalDataService.getRegions()
            }
        } catch (e: HttpException) {
            Log.e("API_ERROR", "HTTP Error: ${e.code()} - ${e.message()}")
            null
        } catch (e: Exception) {
            Log.e("API_ERROR", "Network Error: ${e.message}")
            null
        }
    }

    suspend fun getAllRecipes(): List<Recipe>? {
        return try {
            withContext(Dispatchers.IO) {
                globalDataService.getRecipes().recipes
            }
        } catch (e: HttpException) {
            Log.e("API_ERROR", "HTTP Error: ${e.code()} - ${e.message()}")
            null
        } catch (e: Exception) {
            Log.e("API_ERROR", "Network Error: ${e.message}")
            null
        }
    }
}
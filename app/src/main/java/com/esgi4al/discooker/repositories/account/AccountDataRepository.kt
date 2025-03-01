package com.esgi4al.discooker.repositories.account

import android.util.Log
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.service.AccountService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AccountDataRepository(private var accountService: AccountService) {

    suspend fun getConnectedUserLikedRecipes(): List<Recipe>? {
        return try {
            withContext(Dispatchers.IO) {
                accountService.getLikedRecipesByUser()
            }
        } catch (e: HttpException) {
            Log.e("API_ERROR", "HTTP Error: ${e.code()} - ${e.message()}")
            null
        } catch (e: Exception) {
            Log.e("API_ERROR", "Network Error: ${e.message}")
            null
        }
    }

    suspend fun getConnectedUserRecipes(): List<Recipe>? {
        return try {
            withContext(Dispatchers.IO) {
                accountService.getUserRecipes()
            }
        } catch (e: HttpException) {
            Log.e("API_ERROR", "HTTP Error: ${e.code()} - ${e.message()}")
            null
        } catch (e: Exception) {
            Log.e("API_ERROR", "Network Error: ${e.message}")
            null
        }
    }

    suspend fun deleteUserRecipe(recipeId: String): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                val response = accountService.deleteUserRecipe(recipeId)
                if (response.isSuccessful) {
                    true
                } else {
                    Log.e("API_ERROR", "HTTP Error: ${response.code()} - ${response.message()}")
                    false
                }
            }
        } catch (e: HttpException) {
            Log.e("API_ERROR", "HTTP Error: ${e.code()} - ${e.message()}")
            false
        } catch (e: Exception) {
            Log.e("API_ERROR", "Network Error: ${e.message}")
            false
        }
    }

    suspend fun deleteUserFavoriteRecipe(recipeId: String): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                val response = accountService.unlikeRecipe(recipeId)
                if (response.isSuccessful) {
                    true
                } else {
                    Log.e("API_ERROR", "HTTP Error: ${response.code()} - ${response.message()}")
                    false
                }
            }
        } catch (e: HttpException) {
            Log.e("API_ERROR", "HTTP Error: ${e.code()} - ${e.message()}")
            false
        } catch (e: Exception) {
            Log.e("API_ERROR", "Network Error: ${e.message}")
            false
        }
    }
}
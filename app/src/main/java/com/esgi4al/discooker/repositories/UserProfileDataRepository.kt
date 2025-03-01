package com.esgi4al.discooker.repositories

import android.util.Log
import com.esgi4al.discooker.models.ListableUser
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class UserProfileDataRepository(private var userService: UserService) {

    suspend fun getUserData(userId: String): ListableUser? {
        return try {
            withContext(Dispatchers.IO) {
                userService.getUserData(userId)
            }
        } catch (e: HttpException) {
            Log.e("API_ERROR", "HTTP Error: ${e.code()} - ${e.message()}")
            null
        } catch (e: Exception) {
            Log.e("API_ERROR", "Network Error: ${e.message}")
            null
        }
    }

    suspend fun getUserRecipes(userId: String): List<Recipe>? {
        return try {
            withContext(Dispatchers.IO) {
                userService.getUserRecipes(userId)
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
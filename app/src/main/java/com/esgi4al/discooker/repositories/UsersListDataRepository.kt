package com.esgi4al.discooker.repositories

import android.util.Log
import com.esgi4al.discooker.models.ListableUser
import com.esgi4al.discooker.service.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class UsersListDataRepository {

    private val usersService = ApiClient.getUsersService()

    suspend fun getAllUsers(): List<ListableUser>? {
        return try {
            withContext(Dispatchers.IO) {
                usersService.getAllUsers()
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
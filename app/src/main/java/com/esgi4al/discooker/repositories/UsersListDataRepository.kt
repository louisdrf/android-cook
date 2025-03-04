package com.esgi4al.discooker.repositories

import android.util.Log
import com.esgi4al.discooker.models.ListableUser
import com.esgi4al.discooker.service.UsersService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class UsersListDataRepository(private var usersService: UsersService) {

    suspend fun getUsersBySearch(username: String?): List<ListableUser>? {
        return try {
            withContext(Dispatchers.IO) {
                usersService.getUserBySearch(username)
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
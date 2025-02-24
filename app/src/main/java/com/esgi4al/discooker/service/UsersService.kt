package com.esgi4al.discooker.service

import com.esgi4al.discooker.models.ListableUser
import retrofit2.http.GET

interface UsersService {
    @GET("users")
    suspend fun getAllUsers(): List<ListableUser>

    @GET("users/mostLiked")
    suspend fun getMostLikedUsers(): List<ListableUser>

    @GET("user/mostActive")
    suspend fun getMostActiveUsers(): List<ListableUser>
}
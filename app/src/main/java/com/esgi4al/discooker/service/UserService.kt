package com.esgi4al.discooker.service

import com.esgi4al.discooker.models.ListableUser
import com.esgi4al.discooker.models.Recipe
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("userData/{userId}")
    suspend fun getUserData(@Path("userId") userId: String): ListableUser

    @GET("recipes/user/{userId}")
    suspend fun getUserRecipes(@Path("userId") userId: String): List<Recipe>
}
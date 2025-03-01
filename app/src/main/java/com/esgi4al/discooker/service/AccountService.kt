package com.esgi4al.discooker.service

import com.esgi4al.discooker.models.ApiResponseGetRecipes
import com.esgi4al.discooker.models.Recipe
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AccountService {
    @GET("like/liked-recipes")
    suspend fun getLikedRecipesByUser(): List<Recipe>

    @GET("recipes/user")
    suspend fun getUserRecipes(): List<Recipe>

    @DELETE("recipes/{id}")
    suspend fun deleteUserRecipe(@Path("id") recipeId: String): Response<Unit>

    @DELETE("like/{id}")
    suspend fun unlikeRecipe(@Path("id") recipeId: String): Response<Unit>
}

package com.esgi4al.discooker.service

import com.esgi4al.discooker.models.ApiRequestPostRecipe
import com.esgi4al.discooker.models.CommentRequest
import com.esgi4al.discooker.models.Recipe
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeService {
    @GET("recipes")
    suspend fun getRecipes(): List<Recipe>

    @POST("recipes")
    suspend fun createRecipe(@Body recipe: ApiRequestPostRecipe): Recipe

    @GET("recipes/{id}")
    suspend fun getRecipeDetails(@Path("id") id: String): Recipe

    @POST("recipes/{id}/comments")
    suspend fun postRecipeComment(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body comment: CommentRequest
    ): Recipe

    @GET("like/isLiked/{id}")
    suspend fun isRecipeLiked(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Boolean

    @POST("like/toggle-like/{id}")
    suspend fun toggleLikeRecipe(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Boolean
}
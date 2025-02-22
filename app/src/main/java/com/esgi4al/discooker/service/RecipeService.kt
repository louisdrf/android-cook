package com.esgi4al.discooker.service

import com.esgi4al.discooker.models.CommentRequest
import com.esgi4al.discooker.models.RecipeModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeService {
    @GET("recipes")
    suspend fun getRecipes(): List<RecipeModel>

    @GET("recipes/{id}")
    suspend fun getRecipeDetails(@Path("id") id: String): RecipeModel

    @POST("recipes/{id}/comments")
    suspend fun postRecipeComment(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body comment: CommentRequest
    ): RecipeModel
}
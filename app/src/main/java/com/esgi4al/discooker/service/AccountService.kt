package com.esgi4al.discooker.service

import com.esgi4al.discooker.models.ApiResponseGetRecipes
import com.esgi4al.discooker.models.Recipe
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AccountService {
    @GET("like/liked-recipes")
    fun getLikedRecipesByUser(): Call<ApiResponseGetRecipes>

    @GET("recipes/user")
    fun getUserRecipes(): Call<List<Recipe>>

    @POST("recipes/{id}/like")
    fun likeRecipe(@Path("id") recipeId: String): Call<Void>

    @DELETE("like/{id}")
    fun unlikeRecipe(@Path("id") recipeId: String): Call<Void>
}

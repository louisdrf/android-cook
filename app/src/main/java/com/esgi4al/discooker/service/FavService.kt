package com.esgi4al.discooker.service

import com.esgi4al.discooker.models.ApiResponseGetLikedRecipes
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.models.auth.LoginRequest
import com.esgi4al.discooker.models.auth.RegisterRequest
import com.esgi4al.discooker.models.auth.RegisterResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavService {
    @GET("like/userLikes")
    fun getLikedRecipesByUser(): Call<ApiResponseGetLikedRecipes>
    @POST("recipes/{id}/like")
    fun likeRecipe(@Path("id") recipeId: String): Call<Void>
    @DELETE("like/{id}")
    fun unlikeRecipe(@Path("id") recipeId: String): Call<Void>


}

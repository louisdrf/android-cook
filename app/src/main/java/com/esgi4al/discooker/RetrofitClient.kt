package com.esgi4al.discooker

import com.esgi4al.discooker.models.RecipeModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object RetrofitClient {
    private const val BASE_URL = "http://localhost:8000/"

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    interface RecipeApiService {
        @GET("recipe/id")
        fun getRecipeDetails(): RecipeModel
    }

    object RecipeApi {
        val retrofitService : RecipeApiService by lazy {
            retrofit.create(RecipeApiService::class.java)
        }
    }
}
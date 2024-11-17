package com.esgi4al.discooker.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://192.168.1.153:8000/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getAuthService(): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    fun getRecipeService(): RecipeService {
        return retrofit.create(RecipeService::class.java)
    }
}

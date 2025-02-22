package com.esgi4al.discooker.service

import com.esgi4al.discooker.models.ApiResponseGetRecipes
import com.esgi4al.discooker.models.Category
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.models.Region
import retrofit2.http.GET

interface HomePageGlobalDataService {
    @GET("recipes")
    suspend fun getRecipes(): ApiResponseGetRecipes

    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("regions")
    suspend fun getRegions(): List<Region>
}
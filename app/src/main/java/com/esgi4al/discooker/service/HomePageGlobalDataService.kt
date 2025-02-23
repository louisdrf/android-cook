package com.esgi4al.discooker.service

import com.esgi4al.discooker.models.ApiResponseGetRecipes
import com.esgi4al.discooker.models.Category
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.models.Region
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomePageGlobalDataService {
    @GET("recipes")
    suspend fun getRecipes(): ApiResponseGetRecipes

    @GET("search/recipes")
    suspend fun getRecipesBySearch(
        @Query("recipeName") recipeName: String?,
        @Query("categoryName") categoryName: String?,
        @Query("regionName") regionName: String?
    ): List<Recipe>

    @GET("recipes/category/{categoryName}")
    suspend fun getRecipesByCategory(@Path("categoryName") id: String): List<Recipe>

    @GET("recipes/region/{regionName}")
    suspend fun getRecipesByRegion(@Path("regionName") id: String): List<Recipe>

    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("regions")
    suspend fun getRegions(): List<Region>
}
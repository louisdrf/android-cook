package com.esgi4al.discooker.service

import com.esgi4al.discooker.models.Ingredient
import retrofit2.http.GET

interface IngredientService {
    @GET("ingredients")
    suspend fun getIngredients(): List<Ingredient>
}
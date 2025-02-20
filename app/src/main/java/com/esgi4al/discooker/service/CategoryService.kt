package com.esgi4al.discooker.service

import com.esgi4al.discooker.models.Category
import retrofit2.http.GET

interface CategoryService {
    @GET("categories")
    suspend fun getCategories(): List<Category>
}
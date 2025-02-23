package com.esgi4al.discooker.models

data class ApiResponseGetLikedRecipes(
    val message: String,
    val data: List<Recipe>
)

data class ApiResponseGetRecipes(
    val recipes: List<Recipe>
)


data class Recipe(
    val _id: String,
    val user: User,
    val title: String,
    val description: String,
    val thumbnail: String?,
    val ingredients: List<Ingredient>,
    val instructions: List<Instruction>,
    val category: Category,
    val region: Region,
    val likes: List<String>,
    val createdAt: String,
    val updatedAt: String
)
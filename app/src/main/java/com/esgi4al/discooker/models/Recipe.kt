package com.esgi4al.discooker.models

data class ApiResponseGetLikedRecipes(
    val message: String,
    val data: List<Recipe>
)


data class Recipe(
    val _id: String,
    val user: User,
    val title: String,
    val description: String,
    val ingredients: List<Ingredient>,
    val instructions: List<Instruction>,
    val category: String,
    val region: String,
    val likes: List<String>,
    val createdAt: String,
    val updatedAt: String
)
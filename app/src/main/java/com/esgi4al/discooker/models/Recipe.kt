package com.esgi4al.discooker.models

data class ApiResponseGetRecipes(
    val recipes: List<Recipe>
)


data class ApiRequestPostRecipe(
    val title: String,
    val description: String,
    val ingredients: List<Ingredient>,
    val instructions: List<Instruction>,
    val category: Category,
    val region: Region,
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
    val comments: List<Comment>,
    val createdAt: String,
    val updatedAt: String
)

package com.esgi4al.discooker.models

data class RecipeModel(
    val _id: String,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strMealThumb: String,
    val strInstructions: String,
    val ingredients: List<Ingredient>
)

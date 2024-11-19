package com.esgi4al.discooker.models

data class RecipeModel(
    val _id: String,
    val title: String,
    val category: String,
    val region: String,
    val thumbnail: String?,
    val instructions: List<Instruction>,
    val ingredients: List<Ingredient>
)

package com.esgi4al.discooker.models

data class RecipeModel(
    val id: Int,
    val title: String,
    val category: String,
    val area: String,
    val thumb: String,
    val instructions: String,
    val ingredients: List<String>
)

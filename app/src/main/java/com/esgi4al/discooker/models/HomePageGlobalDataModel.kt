package com.esgi4al.discooker.models

data class HomePageGlobalDataModel(
    val recipes: List<Recipe>,
    val regions: List<Region>,
    val categories: List<Category>
)

package com.esgi4al.discooker.ui.interfaces

interface HomePageItemsClickHandler {
    fun onRecipeClick(recipeId: String)
    fun onCategoryClick(categoryName: String)
    fun onRegionClick(regionName: String)
}
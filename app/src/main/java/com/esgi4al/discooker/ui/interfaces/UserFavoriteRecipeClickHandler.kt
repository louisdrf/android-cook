package com.esgi4al.discooker.ui.interfaces

interface UserFavoriteRecipeClickHandler {
    fun onDeleteFavoriteButtonClick(recipeId: String)
    fun onRecipeClick(recipeId: String)
    fun onRecipeUserClick(userId: String)
}
package com.esgi4al.discooker.models

data class ListableUser(
    val _id: String,
    val username: String,
    val email: String,
    val nbRecipes: Int,
    val nbLikes: Int
)

data class User(
    val _id: String,
    val username: String,
    val email: String
)
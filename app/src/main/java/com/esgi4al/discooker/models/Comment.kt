package com.esgi4al.discooker.models

data class Comment(
    val user: User,
    val content: String
)

data class CommentRequest(
    val content: String
)
package com.esgi4al.discooker.service

import com.esgi4al.discooker.models.ListableUser
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users/{userId}")
    suspend fun getUserData(@Path("userId") userId: String): ListableUser
}
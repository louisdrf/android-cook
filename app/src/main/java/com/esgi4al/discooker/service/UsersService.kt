package com.esgi4al.discooker.service

import com.esgi4al.discooker.models.ListableUser
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersService {
    @GET("search/users")
    suspend fun getUserBySearch(@Query("username") username: String?): List<ListableUser>

    @GET("users/mostLiked")
    suspend fun getMostLikedUsers(): List<ListableUser>

    @GET("user/mostActive")
    suspend fun getMostActiveUsers(): List<ListableUser>
}
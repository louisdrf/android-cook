package com.esgi4al.discooker.service

import com.esgi4al.discooker.models.auth.LoginRequest
import com.esgi4al.discooker.models.auth.RegisterRequest
import com.esgi4al.discooker.models.auth.RegisterResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/signin")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ResponseBody>
    @POST("auth/signup")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>
}

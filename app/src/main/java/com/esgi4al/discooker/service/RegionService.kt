package com.esgi4al.discooker.service

import com.esgi4al.discooker.models.Region
import retrofit2.http.GET

interface RegionService {
    @GET("regions")
    suspend fun getRegions(): List<Region>
}
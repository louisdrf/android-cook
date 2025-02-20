package com.esgi4al.discooker

import android.app.Application
import android.util.Log
import com.esgi4al.discooker.service.ApiClient

class Launcher : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiClient.init(this)
        Log.d("Launcher", "ApiClient initialized in Application class")
    }
}
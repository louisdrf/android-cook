package com.esgi4al.discooker.service

import android.content.Context
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8000/api/"

    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
       sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    private fun getClient(): OkHttpClient {
       val prefs = sharedPreferences
            ?: throw IllegalStateException("ApiClient must be initialized with a context by calling ApiClient.init(context)")

        val token = prefs.getString("auth_token", null)

        return OkHttpClient.Builder()
            .addInterceptor { chain: Interceptor.Chain ->
                val originalRequest = chain.request()
                val authenticatedRequest: Request = if (token != null) {
                    originalRequest.newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                } else {
                    originalRequest
                }
                chain.proceed(authenticatedRequest)
            }
            .build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getClient()) // Appel dynamique pour inclure le dernier token
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getGlobalDataService(): HomePageGlobalDataService {
        return getRetrofit().create(HomePageGlobalDataService::class.java)
    }

    fun getAuthService(): AuthService {
        return getRetrofit().create(AuthService::class.java)
    }
    fun getAccountService(): AccountService {
        return getRetrofit().create(AccountService::class.java)
    }

    fun getRecipeService(): RecipeService {
        return getRetrofit().create(RecipeService::class.java)
    }

    fun getUsersService(): UsersService {
        return getRetrofit().create(UsersService::class.java)
    }
}

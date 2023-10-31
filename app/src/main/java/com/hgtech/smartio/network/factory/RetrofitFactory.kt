package com.hgtech.smartio.network.factory

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitFactory {

    fun create(baseUrl: String): Retrofit =
        Retrofit.Builder().client(getClient()).baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            ).build()

    private fun getClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
//            .connectTimeout(10, TimeUnit.SECONDS)
//            .writeTimeout(10, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .addInterceptor(buildOkHttpInterceptor())
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private fun buildOkHttpInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url
            val newHttpUrl = originalHttpUrl.newBuilder().build()
            val newRequest = originalRequest
                .newBuilder()
                .url(newHttpUrl.toUrl().toString().replace("%3F", "?"))
                .build()
            chain.proceed(newRequest)
        }
    }
}